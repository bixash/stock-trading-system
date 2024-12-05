package com.stockapp.broker.controller;

import com.stockapp.broker.dto.OrderDto;
import com.stockapp.broker.dto.OrderInfo;
import com.stockapp.broker.dto.BaseResponse;
import com.stockapp.broker.models.Order;
import com.stockapp.broker.repository.OrderRepository;
import com.stockapp.broker.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("broker/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final  RestTemplate restTemplate;
    private final OrderService orderService;

    public static final long BROKER_ID = 82;
    private final OrderRepository orderRepository;

    public OrderController(RestTemplate restTemplate,
                           OrderService orderService,
                           OrderRepository orderRepository) {
        this.restTemplate = restTemplate;
        this.orderService = orderService;

        this.orderRepository = orderRepository;
    }

    /*
            GET     /orders/{userId}
            GET     /orders/{userId}/history
            POST    /orders/{userId}
            GET     /orders/{userId}/{orderId}
            DELETE  /orders/{userId}/{orderId}
            PUT     /orders/{userId}/{orderId}
            GET     /orders/activeOrders/{scrip}
       */



    @GetMapping(path="history")
    public void getHistoryOrders(){

//        orderService.getHistoryOrders(clientId);

    }
    @GetMapping()
    public BaseResponse getOrder(@RequestParam("clientId") String clientId,
                                 @RequestParam("status") String status){
        if(Objects.equals(status, "open")){
            return new BaseResponse(false,
                    "Open orders!",
                    true,
                    orderService.getOpenOrders(clientId));
        }else if(Objects.equals(status, "executed")){
            return new BaseResponse(false,
                    "Executed orders!",
                    true,
                    orderService.getExecutedOrders(clientId));
        }
        return new BaseResponse(true, "Failed to get orders", false, null);
    }

    @GetMapping("/{orderId}")
    public BaseResponse getOrderById(@PathVariable("orderId") long orderId){
        Order order = orderService.getOrderById(orderId);
        if(order!= null)
            return new BaseResponse(false,
                    "Open orders!",
                    true,
                    orderService.getOrderById(orderId));

        return new BaseResponse(true, "Failed to get orders", false, null);
    }

    @PostMapping()
    public BaseResponse addOrder(@RequestBody OrderDto orderDto){

        Order order = orderService.mapOrderDtoToOrder(orderDto);
        OrderInfo response = orderService.placeOrder(order);

        if(response.isSuccess()){
            return new BaseResponse(
                    false,
                    response.getMessage(),
                    true,
                    response);
        }
        return new BaseResponse(true, response.getMessage(), false, null );

    }

    @PutMapping()
    public BaseResponse updateOrder(@RequestParam("orderId") long orderId,
                            @RequestBody OrderDto orderDto){

        OrderInfo response= orderService.updateOrder(orderDto, orderId);
        if(response.isSuccess()){
            return new BaseResponse(
                    false,
                    response.getMessage(),
                    true,
                    response);
        }
        return new BaseResponse(true, "response.getMessage()", false, null );

    }

    @DeleteMapping()
    public BaseResponse exitOrder(@RequestParam("orderId") long orderId){

      Order order = orderService.deleteOrder(orderId);
      if(order == null)
          return new BaseResponse(
                    false,
                    "Order deleted!",
                    true,
                    null);
      return new BaseResponse(true, "response.getMessage()", false, null );
    }

    @GetMapping(path="/openOrders/{ticker}")
    public String getOpenOrders(@PathVariable("ticker") String ticker){
        final String uri = STR."http://localhost:8081/exchange/orders/\{ticker}";
        return restTemplate.getForObject(uri, String.class);
    }
}


