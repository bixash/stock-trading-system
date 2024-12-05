package com.stockapp.broker.controller;

import com.stockapp.broker.dto.GTTOrderDto;
import com.stockapp.broker.dto.OrderInfo;
import com.stockapp.broker.dto.BaseResponse;
import com.stockapp.broker.service.GTTService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("broker/gtt-orders")
@CrossOrigin(origins = "http://localhost:5173")
public class GTTController {
    private final GTTService gttService;

    public GTTController(GTTService gttService) {
        this.gttService = gttService;
    }

    @GetMapping()
    public BaseResponse gttOrder(@RequestParam("clientId") String clientId){

        return new BaseResponse(false,
                    "GTT orders!",
                    true,
                    gttService.getGttOrders(clientId));

    }
    @PostMapping()
    public BaseResponse addGttOrder(@RequestBody GTTOrderDto gttOrderDto){
        OrderInfo orderInfo = gttService.getGttOrderInfo(gttOrderDto);
        if(orderInfo.isSuccess()){
            return new BaseResponse(false, orderInfo.getMessage(), true, orderInfo );
        }

        return new BaseResponse(true,"Failed to create the trigger!", false, null );

    }
}
