package com.example.shoppinglistoptimization.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class PriceInquiry implements Serializable {

    @XmlElement
    private String productId;
    @XmlElement
    private int quantity;

    // Constructors, getters, and setters
    public PriceInquiry() {}

    public PriceInquiry(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
