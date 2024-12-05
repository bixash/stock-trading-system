package com.stockapp.broker.config;



import com.stockapp.broker.models.Order;
import com.stockapp.broker.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

//@Configuration
public class OrderConfig {

//    @Bean("OrderConfig")
    CommandLineRunner commandLineRunner(OrderRepository repository) {
        return args -> {

            repository.saveAll(List.of(
                new Order(82,
                        "CNC",
                        "buy",
                        "2024-12-08",
                        "Domingo",
                        250,
                        150,
                        0,
                        150,
                        "limit",
                        "NABIL",
                        "pending",
                        LocalDate.now().toString(),
                        LocalDateTime.now().toString()),

                new Order(82,"CNC",
                        "sell",
                        "2024-12-08",
                        "Flamingo",
                        250,
                        150,
                        0,
                        150,
                        "limit",
                        "NABIL",
                        "pending",
                        LocalDate.now().toString(),
                        LocalDateTime.now().minusMinutes(3).toString()),

                new Order(82,"CNC",
                        "sell",
                        "2024-12-08",
                        "105",
                        250,
                        150,
                        0,
                        150,
                        "limit",
                        "NABIL",
                        "pending",
                        LocalDate.now().toString(),
                        LocalDateTime.now().minusMinutes(2).toString()),

                        new Order(82,"CNC",
                        "sell",
                        "2024-12-08",
                        "105",
                        250,
                        150,
                        0,
                        150,
                        "limit",
                        "NABIL",
                        "pending",
                        LocalDate.now().toString(),
                        LocalDateTime.now().minusMinutes(2).toString()),

                new Order(82,"CNC",
                        "buy",
                        "2024-12-08",
                        "103",
                        250,
                        50,
                        0,
                        50,
                        "market",
                        "NABIL",
                        "pending",
                        LocalDate.now().toString(),
                        LocalDateTime.now().toString()),
                        
                new Order(82,"CNC","sell","2024-12-08",
                        "103",
                        250,
                        50,
                        0,
                        50,
                        "market",
                        "NABIL",
                        "pending",
                        LocalDate.now().toString(),
                        LocalDateTime.now().minusMinutes(4).toString())
            ));
        };
    }
}
