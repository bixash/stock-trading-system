package com.stockapp.broker.config;

import com.stockapp.broker.external.Stock;
import org.springframework.context.ApplicationEvent;

public class StockUpdateEvent extends ApplicationEvent {
    private final Stock stock;

    public StockUpdateEvent(Object source, Stock stock) {
        super(source);
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }
}
