// src/lib/data.ts

// Get the profile data from http://localhost:8088/profiles/123
import {UserProfile} from "@/types";

export async function fetchProfileData(): Promise<UserProfile> {
    const response = await fetch('http://localhost:8088/profiles/123');
    return response.json();
}