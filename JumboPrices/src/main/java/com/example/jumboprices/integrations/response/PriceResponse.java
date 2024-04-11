package com.example.jumboprices.integrations.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "productId",
        "price",
        "storeName"
})
@XmlRootElement(name = "PriceResponse")
public class PriceResponse {
    protected String productId;
    protected double price;

   private String storeName = "Jumbo";

    // Constructors, getters, and setters
    public PriceResponse() {}

    public PriceResponse(String productId, double price) {
        this.productId = productId;
        this.price = price;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "PriceResponse{" +
                "productId='" + productId + '\'' +
                ", price=" + price +
                '}';
    }
}
