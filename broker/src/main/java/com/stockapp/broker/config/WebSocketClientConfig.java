package com.stockapp.broker.config;

import com.stockapp.broker.service.StockStompSessionHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

@Configuration
public class WebSocketClientConfig {

    @Bean
    public WebSocketStompClient webSocketStompClient() {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient;
    }

    @Bean
    public StompSessionHandler stompSessionHandler() {
        return new StockStompSessionHandler();
    }

    @Bean
    public CommandLineRunner commandLineRunner(WebSocketStompClient stompClient,
                                               StompSessionHandler sessionHandler) {
        return args -> {
            String url = "ws://localhost:8081/ws";
            StompSession session = stompClient.connect(url, sessionHandler).get();
            System.out.println(STR."Connected to WebSocket server at: \{url}");
        };
    }

}





