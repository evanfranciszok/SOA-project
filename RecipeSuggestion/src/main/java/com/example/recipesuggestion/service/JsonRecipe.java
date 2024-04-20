package com.example.recipesuggestion.service;

import java.util.List;

public class JsonRecipe {
    private String title;
    private List<String> ingredients;
    private List<String> ner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getNer() {
        return ner;
    }

    public void setNer(List<String> ner) {
        this.ner = ner;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    private List<String> directions;
    // ... other fields ...

    // Getters and setters

}
