package com.stockapp.exchange.controller;

import com.stockapp.exchange.dto.MarketInfo;
import com.stockapp.exchange.dto.Circuit;
import com.stockapp.exchange.dto.MarketDepth;
import com.stockapp.exchange.service.MarketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exchange/market")
@CrossOrigin("http://localhost:5173")
public class MarketController {
    private final SimpMessagingTemplate template;

    private final MarketService marketService;

    public MarketController(SimpMessagingTemplate template, MarketService marketService) {
        this.template = template;
        this.marketService = marketService;
    }

    @GetMapping()
    public MarketInfo getMarketDepthAndCircuit(@RequestParam("ticker") String ticker){
        MarketDepth marketDepth = marketService.getMarketDepth(ticker);
        Circuit circuit = marketService.getCircuit(ticker);
         return new MarketInfo(marketDepth,circuit);
    }

//    @Scheduled(fixedRate = 3000)
    public void sendMarketInfo() {
        template.convertAndSend("/topic/market", marketService.getAllMarketInfo());
    }


}
