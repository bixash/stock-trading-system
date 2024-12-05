package com.stockapp.broker.service;

import com.stockapp.broker.config.PublisherMQConfig;
import com.stockapp.broker.config.SubscriberMQConfig;
import com.stockapp.broker.dto.OrderDto;
import com.stockapp.broker.dto.OrderInfo;
import com.stockapp.broker.external.Stock;
import com.stockapp.broker.models.GTTOrder;
import com.stockapp.broker.models.Order;
import com.stockapp.broker.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.stockapp.broker.controller.OrderController.BROKER_ID;
import static java.lang.Math.abs;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    private final HoldingsService holdingsService;

    private final FundsService fundsService;

    private static final String EXCHANGE_URL = "http://localhost:8081/exchange";



    @Autowired
    @Qualifier("publisherRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public OrderService(RestTemplate restTemplate, OrderRepository orderRepository, HoldingsService holdingsService,
                     FundsService fundsService) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
        this.holdingsService = holdingsService;
        this.fundsService = fundsService;

    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @RabbitListener(queues = SubscriberMQConfig.BROKER_82_ORDER_QUEUE)
    public void consumeOrderSentByExchange(Order order){
        orderRepository.save(order);
    }
    public List<Order> getOpenOrders(String clientId){
        return orderRepository.getOpenOrders(clientId, LocalDate.now().toString());
    }

    public List<Order> getExecutedOrders(String clientId){
        return orderRepository.getExecutedOrders(clientId, LocalDate.now().toString());
    }

    public OrderInfo checkOrderValidity(Order order){

        Stock stock = restTemplate.getForObject(
                        STR."\{EXCHANGE_URL}/stock-prices/today?ticker=\{order.getTicker()}",
                        Stock.class
        );

        assert stock != null;
        float previousClosing = stock.getPreviousClosing();
        float upperCircuit = (float) (previousClosing + (previousClosing*0.1));
        float lowerCircuit = (float) (previousClosing - (previousClosing*0.1));

        if(Objects.equals(order.getOrderType(), "limit")){
            if(order.getPrice() >= lowerCircuit && order.getPrice() <= upperCircuit){
                return getOrderInfo(order);
            }
            return new OrderInfo(
                    false,
                    STR."Price should be within circuit \{lowerCircuit} - \{upperCircuit}");
        } else{
            return getOrderInfo(order);
        }

    }

    private OrderInfo getOrderInfo(Order order) {
        if(Objects.equals(order.getTransactionType(), "sell"))
            return holdingsService.isSellUnitsAvailable(order);
        return fundsService.isFundsAvailable(order);
    }

    public OrderInfo placeOrder(Order order){
        OrderInfo orderInfo = checkOrderValidity(order);
        if(orderInfo.isSuccess()){
            Order savedOrder = orderRepository.saveAndFlush(order);
            rabbitTemplate.convertAndSend(
                    PublisherMQConfig.EXCHANGE_EXCHANGE,
                    PublisherMQConfig.EXCHANGE_KEY,
                    order);
            return new OrderInfo(true, STR."Order id: \{savedOrder.getOrderId()}");

        }
        return new OrderInfo(false, orderInfo.getMessage());
    }



    public Order mapGttToOrder(GTTOrder gttOrder) {

            Order order = new Order();
            order.setBrokerId(BROKER_ID);
            order.setTicker(gttOrder.getTicker());
            order.setClientId(gttOrder.getClientId());
            order.setProductType(gttOrder.getProductType());
            order.setTransactionType(gttOrder.getTransactionType());
            order.setPrice(gttOrder.getPrice());
            order.setOrderQuantity(gttOrder.getOrderQuantity());
            order.setOrderType(gttOrder.getOrderType());
            order.setValidity("day");
            order.setTradedQuantity(0);
            order.setRemainingQuantity(gttOrder.getOrderQuantity());
            order.setStatus("pending");
            order.setOrderDate(LocalDate.now().toString());
            order.setOrderTime(formatter.format(LocalTime.now()));
            return order;
    }
    public Order mapOrderDtoToOrder(OrderDto orderDto) {

        return new Order(
                BROKER_ID,
                orderDto.getProductType(),
                orderDto.getTransactionType(),
                orderDto.getValidity(),
                orderDto.getClientId(),
                orderDto.getPrice(),
                orderDto.getOrderQuantity(),
                0,
                orderDto.getOrderQuantity(),
                orderDto.getOrderType(),
                orderDto.getTicker(),
                "pending",
                LocalDate.now().toString(),
                formatter.format(LocalTime.now()));
    }

    public Order deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
       return getOrderById(orderId);

    }
    public void exitOrder(long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus("cancelled");
        orderRepository.save(order);

    }
    public Order getOrderById(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElse(null);
    }

    public OrderInfo updateOrder(OrderDto orderDto, long orderId) {

        Order order = getOrderById(orderId);
        if(order!=null) {
            order.setOrderQuantity(orderDto.getOrderQuantity());
            order.setPrice(orderDto.getPrice());
            order.setOrderType(orderDto.getOrderType());
            order.setTransactionType(orderDto.getTransactionType());
            return placeOrder(order);
        }

        return new OrderInfo(false, "Failed to update!");
    }
}
