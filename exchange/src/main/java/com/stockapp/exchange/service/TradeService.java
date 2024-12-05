package com.stockapp.exchange.service;


import com.stockapp.exchange.config.PublisherMQConfig;
import com.stockapp.exchange.external.TransactionDto;
import com.stockapp.exchange.models.Trade;
import com.stockapp.exchange.repository.TradeRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class TradeService {

    private final TradeRepository tradeRepo;

    @Autowired
    @Qualifier("publisherRabbitTemplate")
    private RabbitTemplate rabbitTemplate;


    public TradeService(TradeRepository tradeRepo) {
        this.tradeRepo = tradeRepo;

    }

    public void saveAndPublishTrade(Trade savetrade){

        Trade trade = tradeRepo.saveAndFlush(savetrade);

        TransactionDto buyerTransactionDto = new TransactionDto(
                trade.getId(),
                trade.getBuyerId(),
                trade.getTicker(),
                trade.getTradedQuantity(),
                0,
                STR."\{trade.getSellerId()}-\{trade.getTradedValue()}-\{LocalDateTime.now().toString()}",
                trade.getTradedPrice(),
                trade.getTradeDate());

        TransactionDto sellerTransactionDto = new TransactionDto(
                trade.getId(),
                trade.getSellerId(),
                trade.getTicker(),
                0,
                trade.getTradedQuantity(),
                STR."\{trade.getBuyerId()}-\{trade.getTradedValue()}-\{LocalDateTime.now().toString()}",
                trade.getTradedPrice(),
                trade.getTradeDate());

        rabbitTemplate.convertAndSend(
                PublisherMQConfig.BROKER_EXCHANGE,
                PublisherMQConfig.BROKER_82_TRANSACTION_KEY,
                buyerTransactionDto);

        rabbitTemplate.convertAndSend(
                PublisherMQConfig.BROKER_EXCHANGE,
                PublisherMQConfig.BROKER_82_TRANSACTION_KEY,
                sellerTransactionDto);

    }

    public List<Trade> getTradeBooks(){

        return tradeRepo.findAll();
    }


    public List<Trade> getTodayTrades() {
        LocalDate date = LocalDate.now();
        return tradeRepo.findAllTradesByDate(date);
    }

    public List<Trade> getTodayTradesByDate(LocalDate date) {
        return tradeRepo.findAllTradesByDate(date);
    }
}
