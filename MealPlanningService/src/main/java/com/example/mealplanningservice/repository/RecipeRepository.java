package com.example.mealplanningservice.repository;

import com.example.mealplanningservice.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
