package com.stockapp.broker.dto;

public class AuthResponse {
    private String token;
    private String client;

    public AuthResponse(String token, String client) {
        this.token = token;
        this.client = client;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
