package com.stockapp.broker.config;


import com.stockapp.broker.models.Client;
import com.stockapp.broker.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ClientConfig {

    @Bean("ClientConfig")
    CommandLineRunner commandLineRunner(ClientRepository repository){
        return  args -> {

            repository.saveAll(List.of(
                    new Client(
                            "Domingo",
                            82,
                            "$2a$10$4pOqxediXOIVnrq6dYD5EeiS/riSu55buDSx9UF02tairKVTuXhzW",
                            new String[]{"FONE", "LEAF", "YCO", "META", "GOOGL","MSFT",
                                    "NTC", "NCELL", "FUSE", "BHOOS"}
                    ),
                    new Client(
                            "Flamingo",
                            82,
                            "$2a$10$4pOqxediXOIVnrq6dYD5EeiS/riSu55buDSx9UF02tairKVTuXhzW",
                            new String[]{"LEAF", "YCO", "NCELL", "FUSE", "META", "GOOGL","BHOOS","MSFT"}
                    ),
                    new Client(
                            "Gustavo",
                            82,
                            "$2a$10$4pOqxediXOIVnrq6dYD5EeiS/riSu55buDSx9UF02tairKVTuXhzW",
                            new String[]{"FONE", "LEAF", "YCO", "META", "GOOGL","MSFT", "BHOOS"}
                    ))
            );

        };
    }

}
