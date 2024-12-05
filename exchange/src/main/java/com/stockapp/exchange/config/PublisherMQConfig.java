package com.stockapp.exchange.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherMQConfig {

    public static final String BROKER_EXCHANGE = "broker_exchange";

    public static final String BROKER_82_ORDER_QUEUE = "broker82_order_queue";
    public static final String BROKER_82_ORDER_KEY = "broker_order_routing_key.82";

    public static final String BROKER_82_TRANSACTION_QUEUE = "broker82_transaction_queue";
    public static final String BROKER_82_TRANSACTION_KEY = "broker_transaction_routing_key.82";

    public static final String BROKER_83_ORDER_QUEUE = "broker83_order_queue";
    public static final String BROKER_83_TRANSACTION_QUEUE = "broker83_transaction_queue";
    public static final String BROKER_83_ORDER_KEY = "broker_83order_key";
    public static final String BROKER_83_TRANSACTION_KEY = "broker_83transaction_key";


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(BROKER_EXCHANGE);
    }

    @Bean
    public Queue broker82TransactionQueue() {
        return new Queue(BROKER_82_TRANSACTION_QUEUE, true);
    }
    @Bean
    public Queue broker82OrderQueue() {
        return new Queue(BROKER_82_ORDER_QUEUE, true);
    }

    @Bean
    public Queue broker83TransactionQueue() {
        return new Queue(BROKER_83_TRANSACTION_QUEUE, true);
    }
    @Bean
    public Queue broker83OrderQueue() {
        return new Queue(BROKER_83_ORDER_QUEUE, true);
    }

    @Bean
    public Binding broker82TransactionBinding(DirectExchange exchange, Queue broker82TransactionQueue) {
        return BindingBuilder
                .bind(broker82TransactionQueue)
                .to(exchange)
                .with(BROKER_82_TRANSACTION_KEY);
    }
    @Bean
    public Binding broker82OrderBinding(DirectExchange exchange, Queue broker82OrderQueue) {
        return BindingBuilder
                .bind(broker82OrderQueue)
                .to(exchange)
                .with(BROKER_82_ORDER_KEY);
    }

    @Bean
    public Binding broker83TransactionBinding(DirectExchange exchange, Queue broker83TransactionQueue) {
        return BindingBuilder
                .bind(broker83TransactionQueue)
                .to(exchange)
                .with(BROKER_83_TRANSACTION_KEY);
    }

    @Bean
    public Binding broker83OrderBinding(DirectExchange exchange, Queue broker83OrderQueue) {
        return BindingBuilder
                .bind(broker83OrderQueue)
                .to(exchange)
                .with(BROKER_83_ORDER_KEY);
    }

    @Bean("brokerConnectionFactory")
    public ConnectionFactory brokerConnectionFactory() {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");

        return connectionFactory;
    }
    @Bean
    public RabbitAdmin brokerRabbitAdmin() {
        return new RabbitAdmin(brokerConnectionFactory());
    }

    @Bean("publisherRabbitTemplate")
    public RabbitTemplate publisherRabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(brokerConnectionFactory());
        rabbitTemplate.setMessageConverter(RabbitMQConfig.jsonMessageConverter());
        return rabbitTemplate;
    }

}
