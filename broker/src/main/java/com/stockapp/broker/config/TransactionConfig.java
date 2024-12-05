package com.stockapp.broker.config;

import com.stockapp.broker.models.Transaction;
import com.stockapp.broker.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class TransactionConfig {
    @Bean("TransactionConfig")
    CommandLineRunner commandLineRunner(TransactionRepository repository){
        return  args -> {

            repository.saveAll(List.of(
                    new Transaction(
                            "Domingo",
                            "LEAF",
                            150,
                            0,
                            150,
                            "IPO",
                            110,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Domingo",
                            "YCO",
                            50,
                            0,
                            50,
                            "IPO",
                            100,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Domingo",
                            "GOOGL",
                            15,
                            0,
                            15,
                            "IPO",
                            1000,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Domingo",
                            "META",
                            15,
                            0,
                            15,
                            "IPO",
                            500,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Gustavo",
                            "NCELL",
                            100,
                            0,
                            100,
                            "IPO",
                            100,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Gustavo",
                            "FUSE",
                            100,
                            0,
                            100,
                            "IPO",
                            100,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Gustavo",
                            "BHOOS",
                            100,
                            0,
                            100,
                            "IPO",
                            100,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Flamingo",
                            "LEAF",
                            150,
                            0,
                            150,
                            "IPO",
                            110,
                            LocalDate.now().toString()),
                    new Transaction(
                            "Flamingo",
                            "YCO",
                            50,
                            0,
                            50,
                            "IPO",
                            100,
                            LocalDate.now().toString()),

                    new Transaction(
                            "Flamingo",
                            "GOOGL",
                            15,
                            0,
                            15,
                            "IPO",
                            1000,
                            LocalDate.now().toString()
                    ),
                    new Transaction(
                            "Flamingo",
                            "BHOOS",
                            100,
                            0,
                            100,
                            "IPO",
                            100,
                            LocalDate.now().toString()
                    ),

                    new Transaction(
                    "Flamingo",
                    "META",
                    15,
                    0,
                    15,
                    "IPO",
                    500,
                    LocalDate.now().toString())
            )
            );

        };
    }
}
