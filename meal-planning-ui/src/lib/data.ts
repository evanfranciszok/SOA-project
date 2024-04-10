// src/lib/data.ts

// Get the profile data from http://localhost:8088/profiles/123
import {UserProfile} from "@/types";

export async function fetchProfileData(): Promise<UserProfile> {
    const apiBaseUrl = process.env.USER_PROFILE_API_URL as string
    const response = await fetch(`${apiBaseUrl}/profiles/1`);
    return response.json();
}