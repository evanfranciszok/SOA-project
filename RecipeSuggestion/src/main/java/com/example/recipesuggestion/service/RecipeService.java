package com.example.recipesuggestion.service;

import com.example.recipesuggestion.model.Recipe;
import com.example.recipesuggestion.model.UserProfile;
import com.example.recipesuggestion.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Value("${user.profile.api.url}")
    private String userProfileApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Recipe> getRandomRecipes(String userId, int count) {
        UserProfile profile = restTemplate.getForObject(userProfileApiUrl + "/profile/" + userId, UserProfile.class);
        assert profile != null;
        List<String> allergies = profile.getAllergies();

        // MongoDB handles filtering and random selection
        return recipeRepository.findRandomRecipesExcludingIngredients(allergies, count);
    }
}
