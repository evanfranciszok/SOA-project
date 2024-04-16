package com.example.shoppinglistoptimization.app.repository;

import com.example.shoppinglistoptimization.app.model.PriceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.Instant;
import java.util.List;

public interface PriceDataRepository extends MongoRepository<PriceData, String> {
    PriceData findFirstByStoreAndProductIdAndTimestampAfterOrderByTimestampDesc(String store, String productId, Instant timestamp);
}
