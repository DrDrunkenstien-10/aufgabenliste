package com.aufgabenliste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AufgabenlisteApplication {
    public static void main(String[] args) {
        SpringApplication.run(AufgabenlisteApplication.class, args);
    }
}
