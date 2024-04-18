package com.example.shoppinglistoptimization.app.controller;

import com.example.shoppinglistoptimization.integrations.priceinquiry.PriceInquiry;
import com.example.shoppinglistoptimization.app.service.PriceInquirySender;
import com.example.shoppinglistoptimization.integrations.shoppinglistrequest.ShoppingListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendInquiry")
public class PriceInquiryController {

    @JmsListener(destination = "shopping-list-queue")
    public void receiveMessage(ShoppingListRequest request) {
        System.out.println("Received message from ShoppingListOptimization");
        System.out.println("User ID: " + request.getUserId());
        System.out.println("Ingredients: " + request.getIngredients());
    }

    @Autowired
    private PriceInquirySender priceInquirySender;

    @PostMapping("/{store}")
    public String sendInquiry(@PathVariable String store, @RequestBody PriceInquiry inquiry) {
        if (inquiry == null) {
            return "Inquiry is empty";
        }
        String destinationQueue = store + "PriceInquiryQueue";
        priceInquirySender.sendPriceInquiry(destinationQueue, inquiry);
        return "Inquiry sent to " + store;
    }
}