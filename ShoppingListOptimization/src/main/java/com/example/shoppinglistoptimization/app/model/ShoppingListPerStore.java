package com.example.shoppinglistoptimization.app.model;

import java.util.List;

public class ShoppingListPerStore {
    private String storeId;
    private List<String> ingredients;

    public ShoppingListPerStore(String storeId, List<String> ingredients) {
        this.storeId = storeId;
        this.ingredients = ingredients;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
