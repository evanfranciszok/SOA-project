package com.example.mealplanningservice.service;

import com.example.mealplanningservice.client.InventoryClient;
import com.example.mealplanningservice.client.RecipeClient;
import com.example.mealplanningservice.client.ShoppingListClient;
import com.example.mealplanningservice.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MealPlanningService {
    @Autowired
    private RecipeClient recipeClient;
    @Autowired
    private InventoryClient inventoryClient;
    @Autowired
    private ShoppingListClient shoppingListClient;

    /**
     * Asynchronously suggests meals for a user based on their current inventory and retrieves random recipes.
     * This method first fetches the user's inventory and a set of random recipes concurrently.
     * Then, it compares the ingredients required for the recipes against the user's inventory to determine which ingredients are missing.
     * These missing ingredients are then passed to a shopping list optimization service to potentially get suggestions for purchasing.
     *
     * Note: The method currently returns a list of recipes without integrating the optimized shopping list into the results.
     * This is a placeholder implementation that should be expanded to include the integration of the shopping list with the recipes,
     * providing a complete meal planning solution.
     *
     * @param userId the unique identifier for the user for whom meals are being suggested
     * @return a CompletableFuture that, when completed, will provide a list of Recipe objects;
     *         these recipes are selected based on the user's inventory and dietary preferences
     */
    @Async
    public CompletableFuture<List<Recipe>> suggestMeals(String userId) {
        CompletableFuture<List<String>> inventoryFuture = CompletableFuture.supplyAsync(() -> inventoryClient.getInventory(userId));
        CompletableFuture<List<Recipe>> recipesFuture = CompletableFuture.supplyAsync(() -> recipeClient.getRandomRecipes(userId));

        return recipesFuture.thenCombine(inventoryFuture, (recipes, inventory) -> {
            List<String> neededIngredients = recipes.stream()
                    .flatMap(recipe -> recipe.getIngredients().stream())
                    .distinct()
                    .filter(ingredient -> !inventory.contains(ingredient))
                    .collect(Collectors.toList());

            List<String> optimizedShoppingList = shoppingListClient.optimizeShoppingList(neededIngredients);
            // further process to combine recipes and shopping list if needed
            return recipes; // Placeholder, adjust as needed
        });
    }
}
