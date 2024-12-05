package com.stockapp.exchange.dto;

public class AggregatedOrder {
    private float price;
    private float orders;
    private float quantity;

    public AggregatedOrder(float price, float orders, float quantity) {
        this.price = price;
        this.orders = orders;
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getOrders() {
        return orders;
    }

    public void setOrders(float orders) {
        this.orders = orders;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
