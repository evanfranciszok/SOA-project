package com.example.recipesuggestion.model;

import java.util.List;

public class UserProfile {
    private String userId;
    private List<String> allergies;
    private List<String> diet;
    private String firstName;
    private String lastName;
    private String portionSize;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getDiet() {
        return diet;
    }

    public void setDiet(List<String> diet) {
        this.diet = diet;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(String portionSize) {
        this.portionSize = portionSize;
    }

    // Getters and Setters
}
