package com.stockapp.broker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    private String id;
    private long brokerId;
    private String password;
    private String[] watchlist;

    public Client() {
    }

    public Client(String id, long brokerId, String password,
                  String[] watchlist) {
        this.id = id;
        this.brokerId = brokerId;
        this.password = password;
        this.watchlist = watchlist;
    }




    public String[] getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(String[] watchlist) {
        this.watchlist = watchlist;
    }

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}