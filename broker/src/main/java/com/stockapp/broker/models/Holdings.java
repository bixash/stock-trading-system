package com.stockapp.broker.models;

import jakarta.persistence.*;

@Entity
public class Holdings {

    @Id
    @SequenceGenerator(name = "holdings_id_sequence", sequenceName = "holdings_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holdings_id_generator")
    private long id;
    private String clientId;
    private String ticker;
    private float averageCost;
    private long freezeUnits;
    private long freeUnits;
    private long currentUnits;
    private String remarks;


    public Holdings() {
    }

    public Holdings(String clientId, String ticker, float averageCost, long freezeUnits, long freeUnits,
                    long currentUnits,
                    String remarks) {
        this.clientId = clientId;
        this.ticker = ticker;
        this.averageCost = averageCost;
        this.freezeUnits = freezeUnits;
        this.freeUnits = freeUnits;
        this.currentUnits = currentUnits;
        this.remarks = remarks;
    }

    public float getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(float averageCost) {
        this.averageCost = averageCost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public long getFreezeUnits() {
        return freezeUnits;
    }

    public void setFreezeUnits(long freezeUnits) {
        this.freezeUnits = freezeUnits;
    }

    public long getFreeUnits() {
        return freeUnits;
    }

    public void setFreeUnits(long freeUnits) {
        this.freeUnits = freeUnits;
    }

    public long getCurrentUnits() {
        return currentUnits;
    }

    public void setCurrentUnits(long currentUnits) {
        this.currentUnits = currentUnits;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
