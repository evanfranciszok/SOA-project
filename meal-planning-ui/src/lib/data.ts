'use server'
// src/lib/data.ts

import {
    FoodItem,
    FoodItemsResponse,
    DietPreference,
    DietPreferencesResponse,
    UserProfile,
    UserProfileRequest,
    InventoryResponse,
    ProfileResponseError
} from "@/types";
import {UserNotFoundError} from "@/lib/exceptions";

const apiBaseUrl = process.env.USER_PROFILE_API_URL as string;


// Fetch profile data
export async function fetchProfileData(userId: string): Promise<UserProfile> {
    const response = await fetch(`${apiBaseUrl}/profile/${userId}`);
    // Check if the response is not OK and if json has "type": "UserNotFound"
    if (!response.ok) {
        const errorData: ProfileResponseError = await response.json();
        if (errorData.type === 'UserNotFound') {
            throw new UserNotFoundError('User not found');
        }
        throw new Error('Failed to fetch profile data');
    }
    return response.json();
}

// Fetch food items
export async function fetchFoodItems(): Promise<FoodItem[]> {
    const response = await fetch(`${apiBaseUrl}/food`);
    if (!response.ok) {
        throw new Error('Failed to fetch food items');
    }
    const data: FoodItemsResponse = await response.json();
    return data.foodItems;
}

// Fetch diet preferences
export async function fetchDietPreferences(): Promise<DietPreference[]> {
    const response = await fetch(`${apiBaseUrl}/diet`);
    if (!response.ok) {
        throw new Error('Failed to fetch diet preferences');
    }
    const data: DietPreferencesResponse = await response.json();
    return data.diets;
}

// Update user profile
export async function updateUserProfile(userId: string, profileData: UserProfileRequest): Promise<UserProfile> {
    // Print the url to the console and the profile data
    console.log(`${apiBaseUrl}/profile/${userId}`);
    console.log(JSON.stringify(profileData));
    const response = await fetch(`${apiBaseUrl}/profile/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(profileData),
    });
    if (!response.ok) {
        throw new Error('Failed to update profile');
    }
    return response.json();
}

// Fetch inventory for a user
export async function fetchInventory(userId: string): Promise<InventoryResponse> {
    const response = await fetch(`${apiBaseUrl}/inventory/${userId}`);
    if (!response.ok) {
        throw new Error('Failed to fetch inventory');
    }
    return response.json();
}
