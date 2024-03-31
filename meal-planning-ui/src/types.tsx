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