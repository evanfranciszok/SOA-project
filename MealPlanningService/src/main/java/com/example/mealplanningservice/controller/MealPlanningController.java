package com.example.mealplanningservice.controller;

import com.example.mealplanningservice.model.Recipe;
//import com.example.mealplanningservice.service.MealPlanningService;
import com.example.mealplanningservice.service.MealPlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class MealPlanningController {
    @Autowired
    private MealPlanningService mealPlanningService;

//    @GetMapping("/plan-meals")
//    public CompletableFuture<List<Recipe>> planMeals(@RequestParam String userId) {
//        return mealPlanningService.suggestMeals(userId);
//    }


// Get recipes based on user id
    @GetMapping("/getRecipes")
    public String getRecipes(@RequestParam String userId) {
        mealPlanningService.generateRandomRecipes(userId);
        return "Hello World";
    }
}
