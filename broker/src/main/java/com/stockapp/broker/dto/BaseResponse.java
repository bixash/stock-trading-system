package com.stockapp.broker.dto;


public class BaseResponse {
    public boolean error;
    public String msg;
    public boolean success;
    public Object result;

    public BaseResponse(boolean error, String msg, boolean success, Object result) {
        this.error = error;
        this.msg = msg;
        this.success = success;
        this.result = result;
    }

}
