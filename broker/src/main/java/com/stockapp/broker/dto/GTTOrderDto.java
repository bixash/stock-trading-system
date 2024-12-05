package com.stockapp.broker.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class GTTOrderDto {
    private String ticker;
    private String clientId;
    private String productType;
    private String transactionType; // buy or sell
    private float price; // for limit only
    private long orderQuantity;
    private String orderType; // limit or market
    private String triggerType;
    private float triggerPrice;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public float getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(float triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(long orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
