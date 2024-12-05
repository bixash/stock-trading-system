package com.stockapp.broker.controller;

import com.stockapp.broker.dto.BaseResponse;
import com.stockapp.broker.dto.FundsRequest;
import com.stockapp.broker.models.Funds;
import com.stockapp.broker.models.Holdings;
import com.stockapp.broker.service.ClientService;
import com.stockapp.broker.service.FundsService;
import com.stockapp.broker.service.HoldingsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("broker")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {

    private final ClientService clientService;
    private final HoldingsService holdingsService;
    private final FundsService fundsService;

    public ClientController(ClientService clientService, HoldingsService holdingsService, FundsService fundsService) {
        this.clientService = clientService;
        this.holdingsService = holdingsService;
        this.fundsService = fundsService;
    }

    @GetMapping("/watchlist")
    public String[] getWatchList(@RequestParam("clientId") String username){
        return clientService.getWatchList(username);
    }

    @GetMapping("/holdings")
    public List<Holdings> getHoldingsList(@RequestParam("clientId") String username){
        return holdingsService.getAllHoldingsInfo(username);
    }

    @GetMapping("/funds")
    public Funds getFundsInfo(@RequestParam("clientId") String clientId){
        return fundsService.getFundsInfo(clientId);
    }
    @PostMapping("/funds")
    public BaseResponse loadFunds(@RequestBody FundsRequest fundsRequest){
        int updatedRow = fundsService.loadFunds(fundsRequest.clientId(), fundsRequest.cash());
        if(updatedRow > 0){
            return new BaseResponse(
                    false,
                    STR."Rs.\{fundsRequest.cash()} loaded! ",
                    true,
                    updatedRow);
        }
        return new BaseResponse(
                true,
                "Can't load the cash!",
                false,
                null);
    }
}
