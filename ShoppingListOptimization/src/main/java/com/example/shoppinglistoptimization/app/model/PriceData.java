package com.example.shoppinglistoptimization.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "priceData")
public class PriceData {
    @Id
    private String id;

    private String store;
    private String productId;
    private Instant timestamp;
    private double price; // Assume it's a simple price representation

    // Getters and setters
}
