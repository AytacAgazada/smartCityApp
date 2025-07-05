package com.example.smartcityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.smartcityapp.client")
@EnableJpaRepositories(basePackages = "com.example.smartcityapp.repository")
public class SmartCityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCityAppApplication.class, args);
    }
}