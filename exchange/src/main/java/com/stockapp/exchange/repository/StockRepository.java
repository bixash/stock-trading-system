package com.stockapp.exchange.repository;

import com.stockapp.exchange.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

        //2024-03-10T12:33:42.698546
    @Query("SELECT s from Stock s WHERE s.ticker = ?1 and tradeDate =?2")
    Stock getStock(String ticker, String tradeDate);

    @Modifying
    @Query("UPDATE Stock s SET " +
            "s.openingPrice =?3, s.closingPrice=?4, s.lastTradedPrice = ?5," +
            "s.maxPrice = ?6, s.minPrice=?7, s.totalVolume=?8, s.totalAmount =?9," +
            "s.differenceRs=?10, s.percentChange=?11, s.lastTradedVolume=?12, s.noOfTransactions=?13, " +
            "s.lastTradedTime=?14 " +
            "WHERE s.ticker=?1 and s.tradeDate=?2")
    int updateStock(String ticker,
                     String tradeDate,
                     float openingPrice,
                     float closingPrice,
                     float lastTradedPrice,
                     float maxPrice, float minPrice, float totalVolume,
                     float totalAmount, float differenceRs,
                     float percentChange,
                     float lastTradedVolume,
                     float noOfTransactions,
                     String lastTradedTime);

    @Query("SELECT s from Stock s WHERE s.tradeDate=?1 and s.ticker =?2")
    Stock getTodayPrice( String date, String ticker);

    @Query("SELECT s from Stock s WHERE s.tradeDate=:date")
    List<Stock> findStockPricesByDate(@Param("date") String date);

    @Query("SELECT s.ticker from Stock s")
    List<String> getAllStockTicker();
}
