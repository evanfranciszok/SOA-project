package com.example.mealplanningservice.service;

//import com.example.mealplanningservice.model.Recipe;
import com.example.mealplanningservice.integration.reciperequest.RecipeRequest;
import com.example.mealplanningservice.integration.recipesresponse.IngredientType;
import com.example.mealplanningservice.integration.recipesresponse.NerType;
import com.example.mealplanningservice.integration.recipesresponse.Recipes;
import com.example.mealplanningservice.model.Recipe;
import com.example.mealplanningservice.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
        import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MealPlanningService {
//    @Autowired
//    private RecipeClient recipeClient;
//    @Autowired
//    private InventoryClient inventoryClient;
//    @Autowired
//    private ShoppingListClient shoppingListClient;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    JmsTemplate jmsTemplate;


// Method that sends a request to the RecipeClient to get a list of random recipes\
    public void generateRandomRecipes(String userId) {
//        Print the message
        System.out.println("Generating random recipes for user: " + userId);
        RecipeRequest request = new RecipeRequest();
        request.setUserId(userId);
        request.setNumberOfRecipes(7);
        jmsTemplate.convertAndSend("recipe-queue", request);
//        Print the message
        System.out.println("Message sent to recipe-queue");
    }

    @JmsListener(destination = "recipe-response-queue")
    public void receiveRecipes(Recipes recipes) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<LocalDate> weekDays = IntStream.range(0, 7)
                .mapToObj(startOfWeek::plusDays)
                .toList();

        List<Recipe> recipeList = recipes.getRecipe().stream()
                .map(recipeType -> {
                    Recipe recipe = new Recipe(); // Assume conversion from recipeType to Recipe
                    int index = recipes.getRecipe().indexOf(recipeType);
                    // set ingredients, name, etc.
                    List <IngredientType> ingredients = recipeType.getIngredient();
                    List<String> ingredientList = ingredients.stream().map(IngredientType::getValue).toList();
                    recipe.setIngredients(ingredientList);

                    recipe.setName(recipeType.getTitle());
//                    Set NER
                    List<NerType> nerList = recipeType.getNER();
                    List<String> nerTypeList = nerList.stream().map(NerType::getValue).toList();
                    recipe.setNER(nerTypeList);

                    recipe.setDate(weekDays.get(index % 7));
                    return recipe;
                })
                .collect(Collectors.toList());

        recipeRepository.saveAll(recipeList);
    }

}
