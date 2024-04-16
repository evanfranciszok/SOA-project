package com.example.mealplanningservice.client;
import com.example.mealplanningservice.model.Recipe;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "recipeClient", url = "${recipe.suggestion.service.url}")
public interface RecipeClient {
    @GetMapping("/recipes")
    List<Recipe> getRandomRecipes(@RequestParam String userId);
}
