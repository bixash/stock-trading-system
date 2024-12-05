package com.stockapp.exchange.service;


import com.stockapp.exchange.config.PublisherMQConfig;
import com.stockapp.exchange.config.SubscriberMQConfig;
import com.stockapp.exchange.models.Order;
import com.stockapp.exchange.models.Stock;
import com.stockapp.exchange.models.Trade;
import com.stockapp.exchange.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class OrderConsumer {
    private final OrderRepository orderRepository;
    @Autowired
    @Qualifier("publisherRabbitTemplate")
    private RabbitTemplate rabbitTemplate;
    private final TradeService tradeService;
    private final StockService stockService;
    private  final SimpMessagingTemplate template;

    private final MarketService marketService;


    public OrderConsumer(OrderRepository orderRepository,
                         TradeService tradeService,
                         StockService stockService, SimpMessagingTemplate template,
                         MarketService marketService) {
        this.tradeService = tradeService;
        this.orderRepository = orderRepository;
        this.stockService = stockService;
        this.template = template;
        this.marketService = marketService;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    @RabbitListener(queues = SubscriberMQConfig.EXCHANGE_QUEUE)
    public void orderConsumer(Order order){

        if(Objects.equals(order.getStatus(), "pending"))
            order.setStatus("open");
        Order savedOrder = orderRepository.saveAndFlush(order);

        if(Objects.equals(savedOrder.getOrderType(), "limit")){
            executeLimitOrders(savedOrder);
        }else{
            executeMarketOrders(savedOrder);
        }

    }
//    @Async("AsyncExecutor")
    private void executeLimitOrders(Order limit_order) {
        if (Objects.equals(limit_order.getTransactionType(), "buy")) {
            executeBuyLimitOrder(limit_order);
        } else {
            executeSellLimitOrder(limit_order);
        }

    }
//    @Async("AsyncExecutor")
    public void executeMarketOrders(Order market_order) {

        if (Objects.equals(market_order.getTransactionType(), "buy")) {
            executeBuyMarketOrder(market_order);
        } else {
            executeSellMarketOrder(market_order);
        }
    }
    private void executeBuyLimitOrder(Order buy_limit_order) {
        List<Order> sell_limit_orders = orderRepository.findOpenLimitOrder(
                buy_limit_order.getTicker(), buy_limit_order.getPrice(),
                "sell", PageRequest.of(0, 1));

        if (!sell_limit_orders.isEmpty()) {
            Order sell_limit_order = sell_limit_orders.getFirst();
            executeBuyOrder(buy_limit_order, sell_limit_order);
        }
        else{
            publishOrder(buy_limit_order);
        }

    }
    private void executeSellLimitOrder(Order sell_limit_order) {
        List<Order> buy_limit_orders = orderRepository.findOpenLimitOrder(
                sell_limit_order.getTicker(), sell_limit_order.getPrice(),
                "buy", PageRequest.of(0, 1));

        if (!buy_limit_orders.isEmpty()) {
            Order buy_limit_order = buy_limit_orders.getFirst();
            executeSellOrder(buy_limit_order, sell_limit_order);

        }
        else{
            publishOrder(sell_limit_order);
        }


    }
    private void executeSellMarketOrder(Order sell_market_order) {
        List<Order> buy_market_orders = orderRepository.findOpenBuyMarketOrder(
                sell_market_order.getTicker(), PageRequest.of(0, 1));

        List<Order> buy_limit_orders = orderRepository.findOpenBuyLimitOrder(
                sell_market_order.getTicker(), PageRequest.of(0, 1));

        float ltp =  stockService.getTodayPriceByTicker
                (sell_market_order.getTicker()).getLastTradedPrice();

        if (!buy_market_orders.isEmpty()) {
            Order buy_market_order = buy_market_orders.getFirst();
            buy_market_order.setPrice(ltp);
            sell_market_order.setPrice(ltp);
            executeSellOrder(buy_market_order, sell_market_order);
        }
        else if (!buy_limit_orders.isEmpty()) {
            Order buy_limit_order = buy_limit_orders.getFirst();
            sell_market_order.setPrice(buy_limit_order.getPrice());
            executeSellOrder(buy_limit_order, sell_market_order);
        }
        else{
           publishOrder(sell_market_order);
        }
    }


    private void executeBuyMarketOrder(Order buy_market_order) {

        List<Order> sell_market_orders = orderRepository.findOpenSellMarketOrder(
                buy_market_order.getTicker(), PageRequest.of(0, 1));

        List<Order> sell_limit_orders = orderRepository.findOpenSellLimitOrder(
                buy_market_order.getTicker(), PageRequest.of(0, 1));

        float ltp =  stockService.getTodayPriceByTicker
                (buy_market_order.getTicker()).getLastTradedPrice();

        if (!sell_market_orders.isEmpty()) {

            Order sell_market_order = sell_market_orders.getFirst();
            buy_market_order.setPrice(ltp);
            sell_market_order.setPrice(ltp);
            executeBuyOrder(buy_market_order, sell_market_order);
        }
        else if (!sell_limit_orders.isEmpty()) {
            Order sell_limit_order = sell_limit_orders.getFirst();
            buy_market_order.setPrice(sell_limit_order.getPrice());
            executeBuyOrder(buy_market_order, sell_limit_order);
        }
        else {
            publishOrder(buy_market_order);
        }

    }


    private void executeSellOrder(Order buy_order, Order sell_order){

        if (sell_order.getRemainingQuantity() == buy_order.getRemainingQuantity()) {

            sell_order.setTradedQuantity(sell_order.getTradedQuantity() + sell_order.getRemainingQuantity());
            sell_order.setRemainingQuantity(0);
            sell_order.setStatus("complete");

            buy_order.setTradedQuantity(buy_order.getTradedQuantity() + buy_order.getRemainingQuantity());
            buy_order.setRemainingQuantity(0);
            buy_order.setStatus("complete");

        }
        else if (sell_order.getRemainingQuantity() > buy_order.getRemainingQuantity()) {

            sell_order.setTradedQuantity(sell_order.getTradedQuantity() + buy_order.getRemainingQuantity());
            sell_order.setRemainingQuantity(
                    sell_order.getRemainingQuantity() - buy_order.getRemainingQuantity());
            sell_order.setStatus("open");

            buy_order.setTradedQuantity(buy_order.getTradedQuantity() + buy_order.getRemainingQuantity());
            buy_order.setRemainingQuantity(0);
            buy_order.setStatus("complete");

        }
        else {
            buy_order.setTradedQuantity(buy_order.getTradedQuantity() + sell_order.getRemainingQuantity());
            buy_order.setRemainingQuantity(
                    buy_order.getRemainingQuantity() - sell_order.getRemainingQuantity());
            buy_order.setStatus("open");

            sell_order.setTradedQuantity(sell_order.getTradedQuantity() + sell_order.getRemainingQuantity());
            sell_order.setRemainingQuantity(0);
            sell_order.setStatus("complete");

        }
        updateAndPublishMatchedOrder(sell_order, buy_order);

    }
    private void executeBuyOrder(Order buy_order, Order sell_order){

        if (buy_order.getRemainingQuantity() == sell_order.getRemainingQuantity()) {

            buy_order.setTradedQuantity(buy_order.getTradedQuantity() + buy_order.getRemainingQuantity());
            buy_order.setRemainingQuantity(0);
            buy_order.setStatus("complete");

            sell_order.setTradedQuantity(sell_order.getTradedQuantity() + sell_order.getRemainingQuantity());
            sell_order.setRemainingQuantity(0);
            sell_order.setStatus("complete");

        }
        else if (buy_order.getRemainingQuantity() > sell_order.getRemainingQuantity()) {

            buy_order.setTradedQuantity(buy_order.getTradedQuantity() + sell_order.getRemainingQuantity());
            buy_order.setRemainingQuantity(
                    buy_order.getRemainingQuantity() - sell_order.getRemainingQuantity());
            buy_order.setStatus("open");

            sell_order.setTradedQuantity(sell_order.getTradedQuantity() + sell_order.getRemainingQuantity());
            sell_order.setRemainingQuantity(0);
            sell_order.setStatus("complete");

        }
        else {
            sell_order.setTradedQuantity(sell_order.getTradedQuantity() + buy_order.getRemainingQuantity());
            sell_order.setRemainingQuantity(
                    sell_order.getRemainingQuantity() - buy_order.getRemainingQuantity());
            sell_order.setStatus("open");

            buy_order.setTradedQuantity(buy_order.getTradedQuantity() + buy_order.getRemainingQuantity());
            buy_order.setRemainingQuantity(0);
            buy_order.setStatus("complete");

        }

        updateAndPublishMatchedOrder(buy_order, sell_order);
    }
    private void publishOrder(Order order) {
        template.convertAndSend(
                STR."/topic/market/\{order.getTicker()}",
                marketService.getMarketDepth(order.getTicker())
        );
        rabbitTemplate.convertAndSend(
                PublisherMQConfig.BROKER_EXCHANGE,
                PublisherMQConfig.BROKER_82_ORDER_KEY,
                order);

    }
    private void updateAndPublishOrder(Order order) {
      orderRepository.updateOrder(
               order.getOrderId(),
                order.getTradedQuantity(),
                order.getRemainingQuantity(),
                order.getStatus());
      publishOrder(order);

    }


    private void updateAndPublishMatchedOrder(Order primaryOrder, Order secondaryOrder){
        tradeService.saveAndPublishTrade(new Trade(
                secondaryOrder.getClientId(),
                secondaryOrder.getBrokerId(),
                primaryOrder.getBrokerId(),
                primaryOrder.getClientId(),
                primaryOrder.getTicker(),
                LocalDate.now().toString(),
                formatter.format(LocalTime.now()),
                primaryOrder.getPrice(),
                primaryOrder.getTradedQuantity(),
                primaryOrder.getPrice()*primaryOrder.getTradedQuantity()));

        updateAndPublishOrder(secondaryOrder);
        updateAndPublishOrder(primaryOrder);

        stockService.updateStockInfo(primaryOrder.getTicker(),
                primaryOrder.getTradedQuantity(),
                primaryOrder.getPrice(),
                LocalDate.now().toString(),
                formatter.format(LocalTime.now()));

    }


}
