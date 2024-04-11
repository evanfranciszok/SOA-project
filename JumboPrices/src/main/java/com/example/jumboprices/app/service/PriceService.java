package com.example.jumboprices.app.service;

import com.example.jumboprices.integrations.priceinquiry.PriceInquiry;
import com.example.jumboprices.integrations.response.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "jumboPriceInquiryQueue")
    public void receivePriceInquiry(PriceInquiry inquiry) {
        // Simulate price lookup
        double price = simulatePriceLookup(inquiry.getProductId());

//        Print the product id and price to the console
        System.out.println("Received price inquiry for product " + inquiry.getProductId() + ". Price: " + price);
        // Wait 5 seconds to simulate a slow response
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Print to console if something is received
        System.out.println("Received price inquiry for product " + inquiry.getProductId() + ". Price: " + price);

        // Send back the price response
        PriceResponse response = new PriceResponse(inquiry.getProductId(), price);
        jmsTemplate.convertAndSend("priceResponseQueue", response);
    }

    private double simulatePriceLookup(String productId) {
        // Placeholder for actual price lookup logic
        return Math.random() * 100; // Dummy price
    }
}
