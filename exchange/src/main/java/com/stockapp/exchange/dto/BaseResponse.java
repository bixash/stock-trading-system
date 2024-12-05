package com.stockapp.exchange.dto;

import java.util.List;

public class BaseResponse {
    public boolean error;
    public String msg;
    public boolean success;
    public List<Object> result;

    public BaseResponse(boolean error, String msg, boolean success, List<Object> result) {
        this.error = error;
        this.msg = msg;
        this.success = success;
        this.result = result;
    }
}