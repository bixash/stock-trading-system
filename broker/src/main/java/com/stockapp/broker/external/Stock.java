package com.stockapp.broker.external;



public class Stock {


    private String ticker;
    private String lastTradedDateTime;
    private String tradeDate;
    private float openingPrice;
    private float closingPrice;
    private float lastTradedPrice;
    private float maxPrice;
    private float minPrice;
    private float totalVolume;
    private float totalAmount;
    private float differenceRs;
    private float percentChange;
    private float lastTradedVolume;
    private float noOfTransactions;
    private float previousClosing;

    public Stock() {
    }

    public Stock(String ticker, String lastTradedDateTime, String tradeDate, float openingPrice,
                 float closingPrice, float lastTradedPrice, float maxPrice, float minPrice, float totalVolume,
                 float totalAmount, float differenceRs, float percentChange, float lastTradedVolume,
                 float noOfTransactions,
                 float previousClosing) {
        this.ticker = ticker;
        this.lastTradedDateTime = lastTradedDateTime;
        this.tradeDate = tradeDate;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.lastTradedPrice = lastTradedPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.totalVolume = totalVolume;
        this.totalAmount = totalAmount;
        this.differenceRs = differenceRs;
        this.percentChange = percentChange;
        this.lastTradedVolume = lastTradedVolume;
        this.noOfTransactions = noOfTransactions;
        this.previousClosing = previousClosing;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public float getLastTradedPrice() {
        return lastTradedPrice;
    }

    public void setLastTradedPrice(float lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(float closingPrice) {
        this.closingPrice = closingPrice;
    }

    public float getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(float openingPrice) {
        this.openingPrice = openingPrice;
    }

    public float getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(float totalVolume) {
        this.totalVolume = totalVolume;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getDifferenceRs() {
        return differenceRs;
    }

    public void setDifferenceRs(float differenceRs) {
        this.differenceRs = differenceRs;
    }

    public float getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(float percentChange) {
        this.percentChange = percentChange;
    }

    public float getLastTradedVolume() {
        return lastTradedVolume;
    }

    public void setLastTradedVolume(float lastTradedVolume) {
        this.lastTradedVolume = lastTradedVolume;
    }

    public String getLastTradedDateTime() {
        return lastTradedDateTime;
    }

    public void setLastTradedDateTime(String lastTradedDateTime) {
        this.lastTradedDateTime = lastTradedDateTime;
    }

    public float getNoOfTransactions() {
        return noOfTransactions;
    }

    public void setNoOfTransactions(float noOfTransactions) {
        this.noOfTransactions = noOfTransactions;
    }

    public float getPreviousClosing() {
        return previousClosing;
    }

    public void setPreviousClosing(float previousClosing) {
        this.previousClosing = previousClosing;
    }



    
    
    

}