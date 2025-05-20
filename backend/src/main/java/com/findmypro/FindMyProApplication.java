package com.findmypro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.findmypro.model")  // Scans for JPA entities
@EnableJpaRepositories("com.findmypro.repository")  // Scans for repositories
public class FindMyProApplication {
    public static void main(String[] args) {
        SpringApplication.run(FindMyProApplication.class, args);
    }
}