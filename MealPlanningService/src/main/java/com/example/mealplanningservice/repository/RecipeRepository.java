package com.example.mealplanningservice.repository;

import com.example.mealplanningservice.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    @Query("{ 'userId': ?0, 'date': { $gte: ?1, $lte: ?2 } }")
    List<Recipe> findRecipesByUserIdAndDateRange(String userId, LocalDate fromDate, LocalDate toDate);
}