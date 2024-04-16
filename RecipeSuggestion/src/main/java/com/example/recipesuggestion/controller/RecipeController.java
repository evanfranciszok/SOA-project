package com.example.recipesuggestion.controller;

import com.example.recipesuggestion.model.Recipe;
import com.example.recipesuggestion.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public List<Recipe> getRandomRecipes(@RequestParam String userId, @RequestParam(defaultValue = "15") int count) {
        return recipeService.getRandomRecipes(userId, count);
    }
}
