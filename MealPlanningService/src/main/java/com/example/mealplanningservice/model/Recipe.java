package com.example.mealplanningservice.model;

import java.util.List;

public class Recipe {
    private String id;  // Assuming each recipe has a unique identifier
    private String name;  // Name of the recipe
    private List<String> ingredients;  // List of ingredients used in the recipe

    // Constructor
    public Recipe() {
    }

    public Recipe(String id, String name, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
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
