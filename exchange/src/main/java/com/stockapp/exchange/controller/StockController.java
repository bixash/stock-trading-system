package com.stockapp.exchange.controller;


import com.stockapp.exchange.models.Stock;
import com.stockapp.exchange.service.StockService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exchange/stock-prices")
@CrossOrigin("http://localhost:5173")
public class StockController {

    private final StockService stockService;

    private final  SimpMessagingTemplate template;

    @Autowired
    @Qualifier("publisherRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public StockController(StockService stockService, SimpMessagingTemplate template) {
        this.stockService = stockService;
        this.template = template;
    }

    //    @GetMapping(path="/today")
//    public BaseResponse getStockPrices(){
//        rabbitTemplate.convertAndSend(
//                PublisherMQConfig.EXCHANGE,
//                BrokerConfig.getRoutingKey(82),
//                "Test publisher");
//        return new BaseResponse(false,
//                "success",
//                true,
//                stockService.getTodayPrices());
//    }+

    @GetMapping(path="date/{date}")
    public List<Stock> getStockPricesByDate(@PathVariable("date") String date){

        return stockService.getStockPricesByDate(date);
    }
//    @GetMapping(path="/{ticker}")
//    public Stock getStockInfo(@PathVariable("ticker") String ticker){
//        return stockService.getTodayPriceByTicker(ticker);
//    }

    @GetMapping()
    public List<Stock> sendStockPrices() {
        return stockService.getTodayPricesOfAll();
    }


//    @Scheduled(fixedRate = 2000)
    public void sendStockPrice() {
      template.convertAndSend("/topic/stock-prices", stockService.getTodayPricesOfAll());
    }


    @GetMapping(path="/today")
    public Stock getStockByDateAndTicker(@RequestParam("ticker") String ticker){
        return stockService.getTodayPriceByTicker(ticker);
    }

}
