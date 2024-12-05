package com.stockapp.exchange.service;

import com.stockapp.exchange.dto.BaseResponse;
import com.stockapp.exchange.models.*;
import com.stockapp.exchange.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getTodayOrders(Date today) {
        return orderRepository.findOrderByDate(today);
    }

    public List<Order> getActiveOrders(){

        return orderRepository.getActiveOrders();
    }

    public BaseResponse addOrder(Order order) {
        orderRepository.save(order);

        return new BaseResponse(false, "Order placed successful",
                true, Collections.singletonList(order));
    }

    public void updateOrder(Order order) {

    }

    public void deleteOrder(long orderId) {
    }



    public List<Order> findOpenOrdersByTicker(String ticker) {
        return orderRepository.findOpenLimitOrdersByTicker(ticker);
    }
}
