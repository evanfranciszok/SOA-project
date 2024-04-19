package com.example.shoppinglistoptimization.app.repository;

import com.example.shoppinglistoptimization.app.model.ShoppingList;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ShoppingListRepository extends MongoRepository<ShoppingList, String> {
    @Aggregation(pipeline = {
            "{ $match: { 'userId': ?0 } }",
            "{ $sort: { '_id': -1 } }",
            "{ $limit: 1 }"
    })
    ShoppingList findLatestByUserId(String userId);
}