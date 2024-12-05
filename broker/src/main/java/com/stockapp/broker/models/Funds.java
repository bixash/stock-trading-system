package com.stockapp.broker.models;

import jakarta.persistence.*;

@Entity
public class Funds {
    @Id
    @SequenceGenerator(name = "funds_id_sequence", sequenceName = "funds_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funds_id_generator")
    private long id;
    private String clientId;
    private float availableMargin;
    private float usedMargin;
    private float totalMargin;

    public Funds() {
    }

    public Funds(String clientId, float availableMargin, float usedMargin, float totalMargin) {
        this.clientId = clientId;
        this.availableMargin = availableMargin;
        this.usedMargin = usedMargin;
        this.totalMargin = totalMargin;
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

    public float getAvailableMargin() {
        return availableMargin;
    }

    public void setAvailableMargin(float availableMargin) {
        this.availableMargin = availableMargin;
    }

    public float getUsedMargin() {
        return usedMargin;
    }

    public void setUsedMargin(float usedMargin) {
        this.usedMargin = usedMargin;
    }

    public float getTotalMargin() {
        return totalMargin;
    }

    public void setTotalMargin(float totalMargin) {
        this.totalMargin = totalMargin;
    }
}
