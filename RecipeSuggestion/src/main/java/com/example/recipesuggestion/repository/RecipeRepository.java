package com.example.recipesuggestion.repository;

import com.example.recipesuggestion.model.Recipe;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    @Aggregation(pipeline = {
            "{ $match: { 'ingredients': { $nin: ?0 } } }",
            "{ $sample: { size: ?1 } }"
    })
    List<Recipe> findRandomRecipesExcludingIngredients(List<String> ingredients, int count);
}