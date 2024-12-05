package com.stockapp.broker.config;

import com.stockapp.broker.models.Funds;
import com.stockapp.broker.repository.FundsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FundsConfig {
    @Bean("FundsConfig")
    CommandLineRunner commandLineRunner(FundsRepository repository) {
        return args -> {
            repository.saveAll(List.of(
                    new Funds("Domingo",
                            100000.0f,
                            0,
                            100000.0f),
                    new Funds("Flamingo",
                            100000.0f,
                            0,
                            100000.0f),
                    new Funds("Gustavo",
                            100000.0f,
                            0,
                            100000.0f))
            );
        };
    };
}
