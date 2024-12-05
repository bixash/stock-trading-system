package com.stockapp.broker.service;

import com.stockapp.broker.config.StockUpdateEvent;
import com.stockapp.broker.external.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class StockStompSessionHandler extends StompSessionHandlerAdapter {

    @Autowired
    private  GTTService gttService;

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Stock.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Stock stock = (Stock) payload;
        gttService.executeGTTOrders(stock);
        System.out.println("I got triggered!");

    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/stock-prices", this);
    }
}
