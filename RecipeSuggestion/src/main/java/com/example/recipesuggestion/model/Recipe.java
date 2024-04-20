package com.example.recipesuggestion.model;

import com.example.recipesuggestion.integration.recipesresponse.IngredientType;
import com.example.recipesuggestion.integration.recipesresponse.NerType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private List<String> ingredients;
    private String title;
    private List<String> NER;

    public List<String> getNER() {
        return NER;
    }

    public List<NerType> getNerTypes() {
        List<NerType> nerTypes = new ArrayList<>();
        for (String ner : NER) {
            NerType nerType = new NerType();
            nerType.setValue(ner);
            nerTypes.add(nerType);
        }
        return nerTypes;
    }

    public void setNER(List<String> NER) {
        this.NER = NER;
    }

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

    public List<IngredientType> getIngredientTypes() {
        List<IngredientType> ingredientTypes = new ArrayList<>();
        for (String ingredient : ingredients) {
            IngredientType ingredientType = new IngredientType();
            ingredientType.setValue(ingredient);
            ingredientTypes.add(ingredientType);
        }
        return ingredientTypes;
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
