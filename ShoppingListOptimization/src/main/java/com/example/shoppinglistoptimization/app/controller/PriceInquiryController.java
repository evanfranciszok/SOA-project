package com.example.shoppinglistoptimization.app.controller;

import com.example.shoppinglistoptimization.integrations.priceinquiry.PriceInquiry;
import com.example.shoppinglistoptimization.app.service.PriceInquirySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendInquiry")
public class PriceInquiryController {

    @Autowired
    private PriceInquirySender priceInquirySender;

    @PostMapping("/{store}")
    public String sendInquiry(@PathVariable String store, @RequestBody PriceInquiry inquiry) {
        String destinationQueue = store + "PriceInquiryQueue";
        priceInquirySender.sendPriceInquiry(destinationQueue, inquiry);
        return "Inquiry sent to " + store;
    }
}