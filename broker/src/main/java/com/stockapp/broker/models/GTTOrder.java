package com.stockapp.broker.models;


import jakarta.persistence.*;

@Entity
public class GTTOrder {

    @Id
    @SequenceGenerator(name = "gtt_id_sequence", sequenceName = "gtt_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gtt_id_generator")
    private long id;
    private String ticker;
    private String clientId;
    private String productType;
    private String transactionType; // buy or sell
    private float price;
    private long orderQuantity;
    private String orderType; // limit
    private String triggerType;
    private float triggerPrice;
    private String status; //active, triggered
    private String orderDate;
    private String expiryDate;


    public GTTOrder() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
