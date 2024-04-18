package com.example.mealplanningservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableFeignClients  // This enables Feign client scanning
@EnableAsync
public class MealPlanningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealPlanningServiceApplication.class, args);
    }
}
