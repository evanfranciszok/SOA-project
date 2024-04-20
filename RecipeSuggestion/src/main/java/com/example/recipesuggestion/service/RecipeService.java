package com.example.recipesuggestion.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.recipesuggestion.model.Recipe;
import com.example.recipesuggestion.model.UserProfile;
import com.example.recipesuggestion.repository.RecipeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Value("${user.profile.api.url}")
    private String userProfileApiUrl;


    @Autowired
    private MongoTemplate mongoTemplate;


    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        if (mongoTemplate.count(new Query(), Recipe.class) == 0) {
            try {
                uploadRecipes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadRecipes() throws IOException {
        String jsonFile = "/data/recipe_dataset_sample.json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknown properties
        InputStream inputStream = getClass().getResourceAsStream(jsonFile);
        TypeReference<List<JsonRecipe>> typeReference = new TypeReference<>() {};
        List<JsonRecipe> jsonRecipes = mapper.readValue(inputStream, typeReference);

        List<Recipe> recipes = new ArrayList<>();
        for (JsonRecipe jsonRecipe : jsonRecipes) {
            Recipe recipe = new Recipe();
            recipe.setTitle(jsonRecipe.getTitle());
            recipe.setIngredients(jsonRecipe.getIngredients());
            recipe.setNER(jsonRecipe.getNer());
            // ... set other fields ...
            recipes.add(recipe);

            // Print the fields of the JsonRecipe
            System.out.println("Title: " + jsonRecipe.getTitle());
            System.out.println("Ingredients: " + jsonRecipe.getIngredients());
            System.out.println("NER: " + jsonRecipe.getNer());
            // ... print other fields ...
        }

        recipeRepository.saveAll(recipes);
    }

    public List<Recipe> getRandomRecipes(String userId, int count) {
        UserProfile profile = restTemplate.getForObject(userProfileApiUrl + "/profile/" + userId, UserProfile.class);
        assert profile != null;
        List<String> allergies = profile.getAllergies();

        // MongoDB handles filtering and random selection
        return recipeRepository.findRandomRecipesExcludingIngredients(allergies, count);
    }
}
