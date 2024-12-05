package com.stockapp.broker.service;

import com.stockapp.broker.config.SubscriberMQConfig;
import com.stockapp.broker.dto.TransactionDto;
import com.stockapp.broker.models.Holdings;
import com.stockapp.broker.models.Transaction;
import com.stockapp.broker.repository.TransactionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {


    private final HoldingsService holdingsService;
    private final TransactionRepository transactionRepository;

    public TransactionService(HoldingsService holdingsService, TransactionRepository transactionRepository) {
        this.holdingsService = holdingsService;
        this.transactionRepository = transactionRepository;
    }

    @RabbitListener(queues = SubscriberMQConfig.BROKER_82_TRANSACTION_QUEUE)
    public void consumeTransactions(TransactionDto transactionDto){
        float averageCost;
        long newFreezeUnits = 0;
        long newFreeUnits;
        Transaction transaction = saveTransaction(transactionDto);

        Holdings holdings = holdingsService.getHoldingsInfo(
                transaction.getClientId(),
                transaction.getTicker());

        if(holdings != null){
            averageCost = (holdings.getAverageCost() + transaction.getTradedPrice())/2 ;
            if(transaction.getDebitQuantity() == 0 && transaction.getCreditQuantity() > 0) {
                newFreeUnits = holdings.getFreeUnits() + transaction.getCreditQuantity();
                holdingsService.updateHoldingsCurrentAndFreezeUnits(
                        holdings.getId(),
                        averageCost,
                        newFreezeUnits,
                        newFreeUnits,
                        transaction.getBalanceQuantity());
            }else {
                holdingsService.updateHoldingsCurrentAndFreezeUnits(
                        holdings.getId(),
                        averageCost,
                        newFreezeUnits,
                        transaction.getBalanceQuantity(),
                        transaction.getBalanceQuantity());
            }

        } else{
            averageCost = transaction.getTradedPrice();
            newFreeUnits = transaction.getCreditQuantity();
            holdingsService.saveHoldings(new Holdings(
                    transaction.getClientId(),
                    transaction.getTicker(),
                    averageCost,
                    newFreezeUnits,
                    newFreeUnits,
                    transaction.getBalanceQuantity(), "secondary"));
        }
    }

    public Transaction saveTransaction(TransactionDto transactionDto){
        long newBalanceQuantity = 0;
        List<Transaction> lastTransactionList = transactionRepository.getLatestTransactionByTicker(
                transactionDto.clientId(),
                transactionDto.ticker(),
                PageRequest.of(0, 1));

        if(!lastTransactionList.isEmpty()){
            Transaction lastTransaction = lastTransactionList.getFirst();
            if(transactionDto.debitQuantity() == 0 && transactionDto.creditQuantity() > 0){
                newBalanceQuantity = lastTransaction.getBalanceQuantity() + transactionDto.creditQuantity();
            }
            if(transactionDto.debitQuantity() > 0 && transactionDto.creditQuantity() == 0) {
                newBalanceQuantity = lastTransaction.getBalanceQuantity() - transactionDto.debitQuantity();
            }
            return transactionRepository.saveAndFlush(new Transaction(
                    transactionDto.tradeId(),
                    transactionDto.clientId(),
                    transactionDto.ticker(),
                    transactionDto.creditQuantity(),
                    transactionDto.debitQuantity(),
                    newBalanceQuantity,
                    transactionDto.description(),
                    transactionDto.tradedPrice(),
                    transactionDto.tradeDate())
            );
        }

        return transactionRepository.saveAndFlush(new Transaction(
                transactionDto.tradeId(),
                transactionDto.clientId(),
                transactionDto.ticker(),
                transactionDto.creditQuantity(),
                transactionDto.debitQuantity(),
                transactionDto.creditQuantity(),
                transactionDto.description(),
                transactionDto.tradedPrice(),
                transactionDto.tradeDate())
        );

    }





}
