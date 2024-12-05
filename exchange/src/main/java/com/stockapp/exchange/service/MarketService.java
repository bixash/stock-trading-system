package com.stockapp.exchange.service;

import com.stockapp.exchange.dto.AggregatedOrder;
import com.stockapp.exchange.dto.Circuit;
import com.stockapp.exchange.dto.MarketDepth;
import com.stockapp.exchange.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
@Service
public class MarketService {
    private  final StockService stockService;
    private final OrderService orderService;


    public MarketService(StockService stockService, OrderService orderService) {
        this.stockService = stockService;
        this.orderService = orderService;
    }


    public MarketDepth getMarketDepth(String ticker){

        List<Order> orderList = orderService.findOpenOrdersByTicker(ticker);

        ArrayList<AggregatedOrder> marketBids = aggregateOrders(orderList, "buy");
        ArrayList<AggregatedOrder> marketOffers = aggregateOrders(orderList, "sell");

        marketBids.sort((a, b) -> Float.compare(b.getPrice(), a.getPrice()));
        marketOffers.sort((a, b) -> Float.compare(a.getPrice(), b.getPrice()));

        return new MarketDepth(ticker, marketBids, marketOffers);
    }

    private ArrayList<AggregatedOrder> aggregateOrders(List<Order> orderList, String type) {
        Map<Float, AggregatedOrder> aggregatedMap = new HashMap<>();

        for (Order order : orderList) {
            if (!order.getTransactionType().equalsIgnoreCase(type)) continue;

            AggregatedOrder aggregatedOrder = aggregatedMap.getOrDefault(
                    order.getPrice(), new AggregatedOrder(order.getPrice(), 0, 0));
            aggregatedOrder = new AggregatedOrder(
                    aggregatedOrder.getPrice(),
                    aggregatedOrder.getOrders() + 1,
                    aggregatedOrder.getQuantity() + order.getRemainingQuantity());
            aggregatedMap.put(order.getPrice(), aggregatedOrder);
        }


        return new ArrayList<>(aggregatedMap.values());
    }




    public List<MarketDepth> getAllMarketInfo() {
        ArrayList<MarketDepth> marketDepthList = new ArrayList<>();
         List<String> tickerList = stockService.getAllStockTicker();
         for(String ticker: tickerList){
             marketDepthList.add(getMarketDepth(ticker));
         }
         return marketDepthList;
    }



    public Circuit getCircuit(String ticker) {
        Stock stock = stockService.getTodayPriceByTicker(ticker);
        float previousClosing = stock.getPreviousClosing();
        float upperCircuit = (float) (previousClosing + (previousClosing*0.1));
        float lowerCircuit = (float) (previousClosing - (previousClosing*0.1));
        return new Circuit(ticker, upperCircuit, lowerCircuit);
    }
    /*
        if(!orderList.isEmpty()){
        for (int i = 0; i < orderList.size(); i++){
            float totalOrders = 1;
            float totalQuantity = 1;
            float price = orderList.get(i).getPrice();
            String type = orderList.get(i).getTransactionType();
            for(int j = i+1; j < orderList.size()-1; j++){
                if(price == orderList.get(j).getPrice()) {
                    totalOrders = totalOrders + 1;
                    totalQuantity = totalQuantity + orderList
                            .get(j)
                            .getRemainingQuantity();

                }
            }
            if(Objects.equals(type, "buy")){
                marketBids.add(new AggregatedOrder(price, totalOrders, totalQuantity));
            }else{
                marketOffers.add(new AggregatedOrder(price, totalOrders, totalQuantity));
            }

        }

    }

     */
}
