package com.example.shoppinglistoptimization.app.service;

import com.example.shoppinglistoptimization.integrations.priceinquiry.PriceInquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PriceInquirySender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendPriceInquiry(String destination, PriceInquiry inquiry) {
        jmsTemplate.convertAndSend(destination, inquiry);
    }
}
