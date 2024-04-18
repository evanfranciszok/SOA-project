package com.example.shoppinglistoptimization.app.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


// Shopping list that only has the userId and the shoppinglist per store
@Document(collection = "shoppinglists")
public class ShoppingList {
    @Id
    private String id;
    private String userId;
    private List<ShoppingListPerStore> shoppingListPerStore;

    public ShoppingList(String userId, List<ShoppingListPerStore> shoppingListPerStore) {
        this.userId = userId;
        this.shoppingListPerStore = shoppingListPerStore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ShoppingListPerStore> getShoppingListPerStore() {
        return shoppingListPerStore;
    }

    public void setShoppingListPerStore(List<ShoppingListPerStore> shoppingListPerStore) {
        this.shoppingListPerStore = shoppingListPerStore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}