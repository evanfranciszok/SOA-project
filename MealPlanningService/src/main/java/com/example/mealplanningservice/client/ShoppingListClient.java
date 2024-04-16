package com.example.mealplanningservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "shoppingListClient", url = "${shopping.list.optimization.service.url}")
public interface ShoppingListClient {
    @GetMapping("/optimize")
    List<String> optimizeShoppingList(@RequestParam List<String> ingredients);
}