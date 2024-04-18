'use server'
// src/lib/data.ts

import {
    DietPreference,
    FoodItem,
    FoodItemsResponse,
    InventoryResponse, MealType,
    ProfileResponseError,
    UserProfile
} from "@/types";

const apiBaseUrl = process.env.USER_PROFILE_API_URL as string;


// Fetch profile data
export async function fetchProfileData(userId: string): Promise<UserProfile> {
    const response = await fetch(`${apiBaseUrl}/profile/${userId}`);
    // Check if the response is not OK and if json has "type": "UserNotFound"
    if (!response.ok) {
        const errorData: ProfileResponseError = await response.json();
        if (errorData.type === 'UserNotFound') {
            await createNewUserProfile(userId);
            return fetchProfileData(userId);
        }
        throw new Error('Failed to fetch profile data');
    }
    return response.json();
}

async function createNewUserProfile(userId: string) {
    try {
        const data = await updateUserProfile(userId, {
            userId: userId,
            firstName: 'John',
            lastName: 'Doe',
            portionSize: 'medium',
            allergies: [],
            diet: [],
        });
    } catch (err: any) {
        throw new Error('Failed to create new user profile');
    }
};

// Fetch food items
export async function fetchFoodItems(): Promise<FoodItem[]> {
    const response = await fetch(`${apiBaseUrl}/food`);
    if (!response.ok) {
        throw new Error('Failed to fetch food items');
    }
    return await response.json();
}

// Fetch diet preferences
export async function fetchDietPreferences(): Promise<DietPreference[]> {
    console.log(`${apiBaseUrl}/diet`);
    const response = await fetch(`${apiBaseUrl}/diet`);
    if (!response.ok) {
        throw new Error('Failed to fetch diet preferences');
    }
    return await response.json();
}

// Update user profile
export async function updateUserProfile(userId: string, profileData: UserProfile): Promise<UserProfile> {
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
    // Print the response to the console
    console.log(response);
    if (!response.ok) {
        throw new Error('Failed to update profile');
    }
    return response.json();
}

// Fetch inventory for a user
export async function fetchInventory(userId: string): Promise<InventoryResponse> {
    const inventoryApiUrl = process.env.INVENTORY_API_URL as string;
    const response = await fetch(`${inventoryApiUrl}/inventory/${userId}`);
    if (!response.ok) {
        throw new Error('Failed to fetch inventory');
    }
    return response.json();
}

// Fetch meals for a user for the current week
export async function fetchMeals(userId: string): Promise<MealType[]> {
    const mealPlanningApiUrl = process.env.MEAL_PLANNING_API_URL as string;

    // Get the current date
    const now = new Date();

    // Calculate the start and end dates of the week
    const startOfWeek = new Date(now.setDate(now.getDate() - now.getDay()));
    const endOfWeek = new Date(now.setDate(now.getDate() - now.getDay() + 6));

    // Format the dates as yyyy-mm-dd
    const startDate = startOfWeek.toISOString().split('T')[0];
    const endDate = endOfWeek.toISOString().split('T')[0];

    const response = await fetch(`${mealPlanningApiUrl}/retrieveMeals?userId=${userId}&fromDate=${startDate}&toDate=${endDate}`);
    if (!response.ok) {
        throw new Error('Failed to fetch meals');
    }
    return response.json();
}

// Refresh all meals for a user for the current week
export async function refreshMeals(userId: string): Promise<void> {
    const mealPlanningApiUrl = process.env.MEAL_PLANNING_API_URL as string;

    const response = await fetch(`${mealPlanningApiUrl}/updateRecipes?userId=${userId}`, {
        method: 'POST',
    });
    if (!response.ok) {
        throw new Error('Failed to refresh meals');
    }
}