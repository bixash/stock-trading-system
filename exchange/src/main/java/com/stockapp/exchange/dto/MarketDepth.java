package com.stockapp.exchange.dto;


import java.util.ArrayList;

public class MarketDepth {
    private String ticker;
    private ArrayList<AggregatedOrder> bids;
    private ArrayList<AggregatedOrder> offers;

    public ArrayList<AggregatedOrder> getBids() {
        return bids;
    }

    public void setBids(ArrayList<AggregatedOrder> bids) {
        this.bids = bids;
    }

    public ArrayList<AggregatedOrder> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<AggregatedOrder> offers) {
        this.offers = offers;
    }


    public MarketDepth(String ticker, ArrayList<AggregatedOrder> bids,
                       ArrayList<AggregatedOrder> offers) {
        this.ticker = ticker;
        this.bids = bids;
        this.offers = offers;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }




}

