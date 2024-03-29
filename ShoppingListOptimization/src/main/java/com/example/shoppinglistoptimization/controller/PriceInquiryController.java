package com.example.shoppinglistoptimization.controller;

import com.example.shoppinglistoptimization.model.PriceInquiry;
import com.example.shoppinglistoptimization.service.PriceInquirySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceInquiryController {

    @Autowired
    private PriceInquirySender priceInquirySender;

    @PostMapping("/sendInquiry")
    public String sendInquiry(@RequestBody PriceInquiry inquiry) {
        priceInquirySender.sendPriceInquiry("inquiryQueue", inquiry);
        return "Inquiry sent";
    }
}
