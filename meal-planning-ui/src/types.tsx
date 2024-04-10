// app/types.tsx
import { StaticImageData } from "next/image";

export interface Meal {
    id: number;
    name: string;
    image_url?: StaticImageData;
    description?: string;
}

export interface ScheduledMeal extends Meal {
    day: string;
}

export interface InventoryItem {
    name: string;
    quantity: number;
    expiry_date: string; // Consider using Date for actual date handling
}

// Defining the structure for the fetch response for inventory
export interface InventoryResponse {
    userId: string;
    food_inventory: InventoryItem[];
}

// Types for Food Items API
export interface FoodItem {
    id: number;
    name: string;
    category: string;
    nutritionalInfo: {
        calories: number;
        fat: number;
        carbs: number;
        protein: number;
    };
}

export interface FoodItemsResponse {
    foodItems: FoodItem[];
}

// Types for Diet Preferences API
export interface DietPreference {
    id: number;
    type: string;
    description?: string;
}

export interface DietPreferencesResponse {
    diets: DietPreference[];
}

// Types for User Profile API
export interface UserProfile {
    userId: string;
    firstName: string;
    lastName: string;
    portionSize: string;
    allergies?: string[];
    diet?: string[];
}

export interface ProfileResponseError {
    error: string;
    type?: string; // For example: "UserNotFound"
}
export interface UserProfileResponse {
    profile: UserProfile;
}

// Assuming POST and PUT requests for user profiles
export interface UserProfileRequest {
    userId?: string; // Optional for new profiles
    firstName: string;
    lastName: string;
    portionSize: string;
    allergies: string[];
    diet: string[];
}

