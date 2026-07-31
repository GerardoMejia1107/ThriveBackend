package com.gerardo.thrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThriveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThriveApplication.class, args);
    }

}
