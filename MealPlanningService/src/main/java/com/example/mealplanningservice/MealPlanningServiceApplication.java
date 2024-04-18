package com.example.mealplanningservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MealPlanningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealPlanningServiceApplication.class, args);
    }
}
