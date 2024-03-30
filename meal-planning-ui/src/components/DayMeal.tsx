// app/components/DayMeal.tsx

import React from 'react';
import { Meal } from '@/types';
import { ArrowPathIcon } from '@heroicons/react/24/solid';

interface DayMealProps {
    meal: Meal;
    onRefresh: () => void; // Function to refresh the meal
    refreshed: boolean; // Whether the meal has been refreshed
}

export default function DayMeal({ meal, onRefresh, refreshed }: DayMealProps) {
    return (
        <div className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300">
            <div className="p-4">
                <h3 className="text-lg font-semibold mb-2">{meal.name}</h3>
                {meal.description && <p className="text-sm text-gray-500 mb-4">{meal.description}</p>}
                <button
                    onClick={onRefresh}
                    className={`inline-flex items-center justify-center rounded-full p-2 shadow-sm text-white ${refreshed ? 'bg-green-500' : 'bg-blue-500'} hover:bg-blue-400 transition-colors duration-300`}
                    aria-label="Refresh Meal"
                >
                    <ArrowPathIcon className="h-6 w-6" />
                </button>
            </div>
        </div>
    );
}
