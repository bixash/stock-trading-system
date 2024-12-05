package com.stockapp.exchange.models;

import jakarta.persistence.*;


@Entity
public class Trade {
    @Id
    @SequenceGenerator(name= "trade_id_sequence", sequenceName = "trade_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="trade_id_generator" )
    private long id;
    private String buyerId;
    private long buyerBrokerId;
    private long sellerBrokerId;
    private String sellerId;
    private String ticker;
    private String tradeDate;
    private String tradeTime;
    private float tradedPrice;
    private long tradedQuantity;
    private float tradedValue;

    public Trade() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public Trade(String buyerId, long buyerBrokerId, long sellerBrokerId, String sellerId, String ticker,
                 String tradeDate,
                 String tradeTime, float tradedPrice, long tradedQuantity, float tradedValue) {
        this.buyerId = buyerId;
        this.buyerBrokerId = buyerBrokerId;
        this.sellerBrokerId = sellerBrokerId;
        this.sellerId = sellerId;
        this.ticker = ticker;
        this.tradeDate = tradeDate;
        this.tradeTime = tradeTime;
        this.tradedPrice = tradedPrice;
        this.tradedQuantity = tradedQuantity;
        this.tradedValue = tradedValue;
    }

    public long getBuyerBrokerId() {
        return buyerBrokerId;
    }

    public void setBuyerBrokerId(long buyerBrokerId) {
        this.buyerBrokerId = buyerBrokerId;
    }

    public long getSellerBrokerId() {
        return sellerBrokerId;
    }

    public void setSellerBrokerId(long sellerBrokerId) {
        this.sellerBrokerId = sellerBrokerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public float getTradedPrice() {
        return tradedPrice;
    }

    public void setTradedPrice(float tradedPrice) {
        this.tradedPrice = tradedPrice;
    }

    public long getTradedQuantity() {
        return tradedQuantity;
    }

    public void setTradedQuantity(long tradedQuantity) {
        this.tradedQuantity = tradedQuantity;
    }

    public float getTradedValue() {
        return tradedValue;
    }

    public void setTradedValue(float tradedValue) {
        this.tradedValue = tradedValue;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
}