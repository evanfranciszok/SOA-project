package com.example.recipesuggestion;

import com.example.recipesuggestion.integration.reciperequest.RecipeRequest;
import com.example.recipesuggestion.integration.recipesresponse.RecipeType;
import com.example.recipesuggestion.integration.recipesresponse.Recipes;
import com.example.recipesuggestion.model.Recipe;
import com.example.recipesuggestion.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeGenerator {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private RecipeService recipeService;

    @JmsListener(destination = "recipe-queue")
    public void receiveMessage(RecipeRequest message) {
        List<Recipe> recipes = recipeService.getRandomRecipes(message.getUserId(), message.getNumberOfRecipes());

        // Transform Recipe to RecipeType
        List<RecipeType> recs = recipes.stream().map(recipe -> {
            RecipeType rec = new RecipeType();
            rec.setId(recipe.getId());
            rec.setUserId(message.getUserId());
            rec.getIngredient().addAll(recipe.getIngredientTypes());
            rec.getNER().addAll(recipe.getNerTypes());
            rec.setTitle(recipe.getTitle());
            return rec;
        }).toList();
        Recipes recipes_response = new Recipes();
        recipes_response.getRecipe().addAll(recs);
        jmsTemplate.convertAndSend("recipe-response-queue", recipes_response);
    }
}
