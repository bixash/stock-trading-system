package com.stockapp.broker.service;


import com.stockapp.broker.dto.GTTOrderDto;
import com.stockapp.broker.external.Stock;
import com.stockapp.broker.models.GTTOrder;
import com.stockapp.broker.dto.OrderInfo;
import com.stockapp.broker.repository.GTTRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GTTService {
    private final GTTRepository gttRepository;
    private final OrderService orderService;

    public GTTService(GTTRepository gttRepository, OrderService orderService) {
        this.gttRepository = gttRepository;
        this.orderService = orderService;
    }

//    @Async("AsyncExecutor")
    public void executeGTTOrders(Stock stock){
        List<GTTOrder> singleGttOrders = getActiveGttByTickerAndTrigger(
                stock.getTicker(),
                stock.getLastTradedPrice()
        );

        for(GTTOrder gttOrder: singleGttOrders){
            orderService.placeOrder(orderService.mapGttToOrder(gttOrder));
            setStatusTriggered(gttOrder.getId());

        }

    }

    public OrderInfo getGttOrderInfo(GTTOrderDto gttOrderDto) {
        GTTOrder gttOrder = new GTTOrder();
        gttOrder.setTicker(gttOrderDto.getTicker());
        gttOrder.setClientId(gttOrderDto.getClientId());
        gttOrder.setProductType(gttOrderDto.getProductType());
        gttOrder.setTransactionType(gttOrderDto.getTransactionType());
        gttOrder.setPrice(gttOrderDto.getPrice());
        gttOrder.setOrderQuantity(gttOrderDto.getOrderQuantity());
        gttOrder.setOrderType(gttOrderDto.getOrderType());
        gttOrder.setTriggerType(gttOrderDto.getTriggerType());
        gttOrder.setTriggerPrice(gttOrderDto.getTriggerPrice());
        gttOrder.setStatus("active");
        gttOrder.setOrderDate(LocalDate.now().toString());
        gttOrder.setExpiryDate(LocalDate.now().plusDays(365).toString());

        GTTOrder savedGtt = gttRepository.saveAndFlush(gttOrder);
        return new OrderInfo(true, STR."GTT order created: \{savedGtt.getId()}");
    }

    private List<GTTOrder> getActiveGttByTickerAndTrigger(String ticker, float lastTradedPrice) {
            return gttRepository.fetchActiveGttByTickerAndTrigger(ticker, lastTradedPrice, "single");
    }

    private void setStatusTriggered(long id) {
        gttRepository.updateStatusToTriggered(id);
    }

    public List<GTTOrder> getGttOrders(String clientId) {
        return gttRepository.getGttOrdersByClientId(clientId);
    }
}
