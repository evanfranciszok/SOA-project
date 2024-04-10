// app/types.tsx
import {StaticImageData} from "next/image";
export interface Meal {
    id: number;
    name: string;
    image_url?: StaticImageData
    description?: string;
}

export interface ScheduledMeal extends Meal {
    day: string;
}

export interface UserProfile {
    userId: string;
    allergies: string[];
    isVegetarian: boolean;
    dislikedFoods: string[];
}

export interface InventoryItem {
    name: string;
    quantity: number;
    expiry_date: string; // Assuming this is the format you want, but consider using Date for actual date handling
}

// Defining the structure for the fetch response
export interface InventoryResponse {
    userId: string;
    food_inventory: InventoryItem[];
}