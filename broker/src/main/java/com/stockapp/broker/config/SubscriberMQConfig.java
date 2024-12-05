package com.stockapp.broker.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SubscriberMQConfig {

    public static final String BROKER_EXCHANGE = "broker_exchange";
    public static final String BROKER_82_TRANSACTION_QUEUE = "broker82_transaction_queue";
    public static final String BROKER_82_TRANSACTION_KEY = "broker_transaction_routing_key.82";
    public static final String BROKER_82_ORDER_QUEUE = "broker82_order_queue";
    public static final String BROKER_82_ORDER_KEY = "broker_order_routing_key.82";


    @Bean
    public DirectExchange brokerExchange() {
        return new DirectExchange(BROKER_EXCHANGE);
    }


    @Bean
    public Queue broker82TransactionQueue() {
        return new Queue(BROKER_82_TRANSACTION_QUEUE, true);
    }

    @Bean
    public Binding broker82TransactionBinding(DirectExchange brokerExchange, Queue broker82TransactionQueue) {
        return BindingBuilder.bind(broker82TransactionQueue).to(brokerExchange).with(BROKER_82_TRANSACTION_KEY);
    }

    @Bean
    public Queue broker82OrderQueue() {
        return new Queue(BROKER_82_ORDER_QUEUE, true);
    }

    @Bean
    public Binding broker82OrderBinding(DirectExchange brokerExchange, Queue broker82OrderQueue) {
        return BindingBuilder.bind(broker82OrderQueue).to(brokerExchange).with(BROKER_82_ORDER_KEY);
    }


    @Bean("brokerConnectionFactory")
    @Primary
    public ConnectionFactory brokerConnectionFactory() {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");

        return connectionFactory;
    }
    @Bean
    @Primary
    public RabbitAdmin brokerRabbitAdmin() {
        return new RabbitAdmin(brokerConnectionFactory());
    }


    @Bean("subscriberRabbitTemplate")
    @Primary
    public RabbitTemplate subscriberRabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(brokerConnectionFactory());
        rabbitTemplate.setMessageConverter(AppConfig.jsonMessageConverter());
        return rabbitTemplate;
    }

}
