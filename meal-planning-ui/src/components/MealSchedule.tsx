'use client'
import React from 'react';
import {ScheduledMeal} from '@/types';
import MealItem from "@/components/MealItem"; // Assume this is the correct import path
import LasagnaImage from '../../public/meals/lasagna.webp';
import TacoImage from '../../public/meals/tacos.webp';
import ChickenStirFryImage from '../../public/meals/chickenstirfry.webp';
import SaladImage from '../../public/meals/salad.webp';

async function MealSchedule() {
    const meals: ScheduledMeal[] = [
        {
            id: 1,
            day: 'Monday',
            name: 'Lasagna Bolognese',
            image_url: LasagnaImage,
        },
        {
            id: 2,
            day: 'Tuesday',
            name: 'Real good Tacos',
            image_url: TacoImage,
        },
        {
            id: 3,
            day: 'Wednesday',
            name: 'Chicken Stir Fry',
            image_url: ChickenStirFryImage,
        },
        {
            id: 4,
            day: 'Thursday',
            name: 'Salad',
            image_url: SaladImage,
        },
    ];

    return (
        <div className="py-6">
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                {meals.map((meal) => (
                    <MealItem key={meal.id} meal={meal} />
                ))}
            </div>
        </div>
    );
}

export default MealSchedule;