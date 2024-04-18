package com.example.shoppinglistoptimization.app.service;

import com.example.shoppinglistoptimization.app.model.ShoppingList;
import com.example.shoppinglistoptimization.app.model.ShoppingListPerStore;
import com.example.shoppinglistoptimization.app.repository.ShoppingListRepository;
import com.example.shoppinglistoptimization.integrations.shoppinglistrequest.ShoppingListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingListOptimizer {
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    public void optimizeShoppingList(ShoppingListRequest shoppingListRequest) {
        // Random optimization algorithm, split between two stores
        List<String> ingredients = shoppingListRequest.getIngredients();

        ShoppingListPerStore store1 = new ShoppingListPerStore("store1", ingredients.subList(0, ingredients.size() / 2));
        ShoppingListPerStore store2 = new ShoppingListPerStore("store2", ingredients.subList(ingredients.size() / 2, ingredients.size()));

        // Save the shopping list per store
         shoppingListRepository.save(new ShoppingList(shoppingListRequest.getUserId(), List.of(store1, store2)));

    }

}
