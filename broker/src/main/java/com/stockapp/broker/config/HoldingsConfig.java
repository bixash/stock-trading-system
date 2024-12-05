package com.stockapp.broker.config;


import com.stockapp.broker.models.Holdings;
import com.stockapp.broker.repository.HoldingsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HoldingsConfig {
    @Bean("HoldingsConfig")
    CommandLineRunner commandLineRunner(HoldingsRepository repository){
        return  args -> {

            repository.saveAll(List.of(
                    new Holdings(
                            "Domingo",
                            "LEAF",
                            110,
                            0,
                            150,
                            150,
                            "IPO"
                    ),
                    new Holdings(
                            "Domingo",
                            "YCO",
                            50,
                            0,
                            50,
                            50,
                            "IPO"
                    ),
                    new Holdings(
                            "Domingo",
                            "GOOGL",
                            1000,
                            0,
                            15,
                            15,
                            "IPO"
                    ),
                    new Holdings(
                            "Domingo",
                            "META",
                            500,
                            0,
                            15,
                            15,
                            "IPO"
                    ),
                    new Holdings(
                            "Gustavo",
                            "NCELL",
                            100,
                            0,
                            100,
                            100,
                            "IPO"
                    ),

                    new Holdings(
                            "Gustavo",
                            "FUSE",
                            100,
                            0,
                            100,
                            100,
                            "IPO"
                    ),
                    new Holdings(
                            "Gustavo",
                            "BHOOS",
                            100,
                            0,
                            100,
                            100,
                            "IPO"
                    ),
                    new Holdings(
                            "Flamingo",
                            "LEAF",
                            110,
                            0,
                            150,
                            150,
                            "IPO"
                    ),
                    new Holdings(
                            "Flamingo",
                            "YCO",
                            100,
                            0,
                            50,
                            50,
                            "IPO"
                    ),
                    new Holdings(
                            "Flamingo",
                            "GOOGL",
                            1000,
                            0,
                            15,
                            15,
                            "IPO"
                    ),
                    new Holdings(
                            "Flamingo",
                            "META",
                            500,
                            0,
                            15,
                            15,
                            "IPO"
                    ),
                    new Holdings(
                            "Flamingo",
                            "BHOOS",
                            100,
                            0,
                            100,
                            100,
                            "IPO"
                    ))

            );

        };
    }
}
