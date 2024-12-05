package com.stockapp.exchange.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Broker {

    @Id
    private long brokerId;
    private String brokerName;


}
