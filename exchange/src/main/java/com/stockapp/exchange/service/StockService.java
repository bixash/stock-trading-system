package com.stockapp.exchange.service;

import com.stockapp.exchange.models.Stock;
import com.stockapp.exchange.repository.StockRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private  final SimpMessagingTemplate template;

    public StockService(StockRepository stockRepository, SimpMessagingTemplate template) {
        this.stockRepository = stockRepository;
        this.template = template;
    }

    @Transactional
    public void updateStockInfo(String ticker, float tradedQuantity, float lastTradedPrice, String tradeDate,
                                String lastTradedTime) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Stock prevStockInfo = stockRepository.getStock(ticker, tradeDate);

        float openingPrice = prevStockInfo.getOpeningPrice();
        float maxPrice = prevStockInfo.getMaxPrice();
        float minPrice = prevStockInfo.getMinPrice();

        if(prevStockInfo.getOpeningPrice() == 0) openingPrice = lastTradedPrice;
        if(prevStockInfo.getMaxPrice() < lastTradedPrice) maxPrice = lastTradedPrice;
        if(prevStockInfo.getMinPrice() > lastTradedPrice || prevStockInfo.getMinPrice() == 0)
            minPrice = lastTradedPrice;

        float totalVolume= prevStockInfo.getTotalVolume()+ tradedQuantity;
        float totalAmount= prevStockInfo.getTotalAmount() + (tradedQuantity * lastTradedPrice);
        float differenceRs = Float.parseFloat(
                decimalFormat.format(lastTradedPrice-prevStockInfo.getLastTradedPrice()));
        float percentChange = Float.parseFloat(decimalFormat.format(Math.abs(differenceRs / lastTradedPrice)*100));
        float noOfTransactions= prevStockInfo.getNoOfTransactions() + 1;


        int updatedRow = stockRepository.updateStock(ticker, tradeDate, openingPrice, lastTradedPrice,
                lastTradedPrice, maxPrice, minPrice, totalVolume, totalAmount, differenceRs,
                percentChange, tradedQuantity, noOfTransactions, lastTradedTime);
        if(updatedRow > 0){
//            template.convertAndSend(STR."/topic/stock-prices/\{ticker}", getTodayPriceByTicker(ticker));
            template.convertAndSend("/topic/stock-prices", getTodayPriceByTicker(ticker));
        }
    }


    public List<Stock> getTodayPricesOfAll() {
        String date = LocalDate.now().toString();
        return stockRepository.findStockPricesByDate(date);
    }

    public Stock getTodayPriceByTicker(String ticker) {
        String date =  LocalDate.now().toString();
        return stockRepository.getTodayPrice(date, ticker);
    }

    public List<Stock> getStockPricesByDate(String date) {

        return stockRepository.findStockPricesByDate(date);
    }


    public List<String> getAllStockTicker() {
        return stockRepository.getAllStockTicker();
    }
}
