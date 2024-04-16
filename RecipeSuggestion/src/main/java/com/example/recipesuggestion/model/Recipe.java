package com.example.recipesuggestion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private List<String> ingredients;
    private String title;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // toString
    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", ingredients=" + ingredients +
                ", title='" + title + '\'' +
                '}';
    }
}
