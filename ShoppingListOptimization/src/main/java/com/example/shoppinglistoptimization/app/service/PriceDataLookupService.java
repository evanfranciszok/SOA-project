package com.example.shoppinglistoptimization.app.service;

import com.example.shoppinglistoptimization.integrations.priceinquiry.PriceInquiry;
import com.example.shoppinglistoptimization.app.model.PriceData;
import com.example.shoppinglistoptimization.app.repository.PriceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class PriceDataLookupService {

    @Autowired
    private PriceDataRepository priceDataRepository;

    // Value fetched from application.properties, defaults to 24 hours if not configured
    @Value("${priceData.recent.threshold.hours:24}")
    private long recentThresholdHours;

    public PriceData getRecentPriceData(String store, PriceInquiry inquiry) {
        Instant recentThreshold = Instant.now().minus(recentThresholdHours, ChronoUnit.HOURS);

        // Assuming PriceData has fields like `store`, `productId`, and `timestamp`
        // and PriceInquiry contains a `productId` field or similar identifier
        return priceDataRepository.findFirstByStoreAndProductIdAndTimestampAfterOrderByTimestampDesc(
                store, inquiry.getProductId(), recentThreshold);
    }
}
