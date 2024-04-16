package com.example.mealplanningservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventoryClient", url = "${inventory.service.url}")
public interface InventoryClient {
    @GetMapping("/inventory")
    List<String> getInventory(@RequestParam String userId);
}