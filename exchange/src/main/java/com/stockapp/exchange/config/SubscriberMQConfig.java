package com.stockapp.exchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SubscriberMQConfig {


    public static final String EXCHANGE_EXCHANGE = "exchange_exchange";
    public static final String EXCHANGE_KEY = "exchange_key";
    public static final String EXCHANGE_QUEUE = "exchange_order_queue";


    @Bean
    public DirectExchange exchangeExchange() {
        return new DirectExchange(EXCHANGE_EXCHANGE);
    }

    @Bean
    public Queue exchangeQueue() {
        return new Queue(EXCHANGE_QUEUE, true);
    }

    @Bean
    public Binding exchangeBinding(DirectExchange exchangeExchange, Queue exchangeQueue) {
        return BindingBuilder.bind(exchangeQueue).to(exchangeExchange).with(EXCHANGE_KEY);
    }


    @Bean("exchangeConnectionFactory")
    @Primary
    public ConnectionFactory exchangeConnectionFactory() {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        connectionFactory.setPort(5671);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");

        return connectionFactory;
    }
    @Bean
    @Primary
    public RabbitAdmin exchangeRabbitAdmin() {
        return new RabbitAdmin(exchangeConnectionFactory());
    }
    @Bean("subscriberRabbitTemplate")
    @Primary
    public RabbitTemplate subscriberRabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(exchangeConnectionFactory());
        rabbitTemplate.setMessageConverter(RabbitMQConfig.jsonMessageConverter());
        return rabbitTemplate;
    }
}
