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