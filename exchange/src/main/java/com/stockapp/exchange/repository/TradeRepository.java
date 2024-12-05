package com.stockapp.exchange.repository;


import com.stockapp.exchange.models.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {


    @Query("SELECT t from Trade t WHERE t.tradeDate = ?1")
    List<Trade> findAllTradesByDate(LocalDate date);
}