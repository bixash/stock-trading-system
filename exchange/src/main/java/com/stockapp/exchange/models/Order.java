package com.stockapp.exchange.models;

import jakarta.persistence.*;


@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    private long orderId;
    private long brokerId;
    private String productType;  // Cash and Carry
    private String transactionType; // buy or sell
    private String validity; //day, minutes, ioc
    private String clientId;
    private float price; // for limit only
    private long orderQuantity;
    private long tradedQuantity;
    private long remainingQuantity;
    private String orderType; // limit or market
    private String ticker;
    private String status; //pending, cancelled, active, completed
    private String orderDate;
    private String orderTime;


    public Order() {
    }

    public Order(long brokerId, String productType, String transactionType, String validity, String clientId,
                 float price,
                 long orderQuantity, long tradedQuantity, long remainingQuantity, String orderType, String ticker,
                 String status, String orderDate, String orderTime) {
        this.brokerId = brokerId;
        this.productType = productType;
        this.transactionType = transactionType;
        this.validity = validity;
        this.clientId = clientId;
        this.price = price;
        this.orderQuantity = orderQuantity;
        this.tradedQuantity = tradedQuantity;
        this.remainingQuantity = remainingQuantity;
        this.orderType = orderType;
        this.ticker = ticker;
        this.status = status;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
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

    public long getTradedQuantity() {
        return tradedQuantity;
    }

    public void setTradedQuantity(long tradedQuantity) {
        this.tradedQuantity = tradedQuantity;
    }

    public long getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(long remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
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

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}


