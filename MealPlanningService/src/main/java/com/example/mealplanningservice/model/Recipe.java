package com.example.mealplanningservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
public class Recipe {
    @Id
    private String id;  // Assuming each recipe has a unique identifier
    private String name;  // Name of the recipe
    private List<String> ingredients;  // List of ingredients used in the recipe
    private List<String> NER; // Named Entity Recognition
    private LocalDate date; // Date for the meal plan

    // Constructor
    public Recipe() {
    }

    public Recipe(String id, String name, List<String> ingredients, LocalDate date) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.date = date;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getNER() {
        return NER;
    }

    public void setNER(List<String> NER) {
        this.NER = NER;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    // toString method for logging and debugging purposes
    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
