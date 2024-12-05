package com.stockapp.exchange.controller;


import com.stockapp.exchange.dto.BaseResponse;
import com.stockapp.exchange.dto.Circuit;
import com.stockapp.exchange.models.Order;
import com.stockapp.exchange.service.OrderService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("exchange/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    /*
            GET     /orders/{scrip}
            POST    /orders
            PUT     /orders/{orderId}
            DELETE  /orders/{orderId}

    */
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;

    }

    @PostMapping()
    public String saveOrder(@RequestBody Order order){

      BaseResponse response = orderService.addOrder(order);

       if(!response.error && response.success){
           return response.msg;
       }
        return "Failed";
    }

    @PutMapping(path="{orderId}")
    public void updateOrder(@PathVariable long orderId, @RequestBody Order order){
        orderService.updateOrder(order);
    }
    @DeleteMapping(path="{orderId}")
    public void deleteOrder(@PathVariable long orderId){
        orderService.deleteOrder(orderId);
    }


}
