package com.stockapp.exchange.controller;


import com.stockapp.exchange.models.Trade;
import com.stockapp.exchange.service.TradeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("exchange/trades")
public class TradeController {

    private final TradeService tradeService;


    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }


    @GetMapping(path="today")
    public List<Trade> getAllTrades(){

        return tradeService.getTodayTrades();
    }
    @GetMapping(path="{date}")
    public List<Trade> getAllTradesByDate(@PathVariable("date") LocalDate date){

        return tradeService.getTodayTradesByDate(date);
    }



}
