package com.example.jumboprices.integrations.priceinquiry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "productId",
        "quantity"
})
@XmlRootElement(name = "PriceInquiry")
public class PriceInquiry implements Serializable {

    private String productId;
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

    @Override
    public String toString() {
        return "PriceInquiry{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
