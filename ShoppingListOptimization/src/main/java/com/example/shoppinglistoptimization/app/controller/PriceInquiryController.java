package com.example.shoppinglistoptimization.app.controller;

import com.example.shoppinglistoptimization.app.model.ShoppingList;
import com.example.shoppinglistoptimization.app.repository.ShoppingListRepository;
import com.example.shoppinglistoptimization.app.service.ShoppingListOptimizer;
import com.example.shoppinglistoptimization.integrations.priceinquiry.PriceInquiry;
import com.example.shoppinglistoptimization.app.service.PriceInquirySender;
import com.example.shoppinglistoptimization.integrations.shoppinglistrequest.ShoppingListRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

@RestController
public class PriceInquiryController {

    @Autowired
    ShoppingListOptimizer shoppingListOptimizer;

    @Autowired
    ShoppingListRepository shoppingListRepository;

    @JmsListener(destination = "shopping-list-queue")
    public void receiveMessage(ShoppingListRequest request) {

        shoppingListOptimizer.optimizeShoppingList(request);
        System.out.println("Received message from ShoppingListOptimization");
        System.out.println("User ID: " + request.getUserId());
        System.out.println("Ingredients: " + request.getIngredients());
    }

    @Autowired
    private PriceInquirySender priceInquirySender;

    @PostMapping("/sendInquiry/{store}")
    public String sendInquiry(@PathVariable String store, @RequestBody PriceInquiry inquiry) {
        if (inquiry == null) {
            return "Inquiry is empty";
        }
        String destinationQueue = store + "PriceInquiryQueue";
        priceInquirySender.sendPriceInquiry(destinationQueue, inquiry);
        return "Inquiry sent to " + store;
    }

    @GetMapping("/shoppingLists")
    public ResponseEntity<String> getShoppingLists(@RequestParam String userId) {
        try {
            ShoppingList latestShoppingList = shoppingListRepository.findLatestByUserId(userId);
            if (latestShoppingList == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{}"); // Return empty JSON if no shopping lists found
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(latestShoppingList);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{}"); // Return empty JSON if there is an error
        }
    }
}