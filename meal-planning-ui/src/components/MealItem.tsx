import React, {useState} from 'react';
import {ScheduledMeal} from "@/types";
import Image from "next/image";
import {ArrowPathIcon} from "@heroicons/react/24/outline";
import ChangeMealDrawer from "@/components/ChangeMealDrawer";
import LasagnaImage from '../../public/meals/lasagna.webp';
interface MealItemProps {
    meal: ScheduledMeal;
}

const MealItem = ({meal}: MealItemProps) => {
    const [selectedMeal, setSelectedMeal] = useState<ScheduledMeal | null>(null);
    const [isDrawerOpen, setDrawerOpen] = useState(false);
    const [isRefreshing, setRefreshing] = useState(false);
    // Check if the image is not found, if so, set the image to a default image
    if (meal.image_url === undefined) {
        meal.image_url = LasagnaImage
    }

    const handleRefresh = () => {
        setSelectedMeal(meal);
        setDrawerOpen(true);
    }



    return (
        <div className="">
            <div className="w-full h-40 md:mb-6 rounded-full">
                <Image
                    src={meal.image_url}
                    alt={meal.name}
                    className="rounded-md"
                />
            </div>
            <div className="lg:py-6">
                <div className="justify-between flex">
                    <h3 className="font-semibold text-lg">{meal.day}</h3>
                    <button
                        onClick={handleRefresh}
                        className="inline-flex items-center"
                        aria-label="Refresh Meal"
                    >
                        <ArrowPathIcon className="h-5 w-5 text-gray-700"/>
                    </button>
                </div>
                <p className="text-gray-700">{meal.name}</p>
            </div>
            <ChangeMealDrawer isDrawerOpen={isDrawerOpen} setDrawerOpen={setDrawerOpen} selectedMeal={selectedMeal} setRefreshing={setRefreshing} isRefreshing={isRefreshing}/>
        </div>
    );
};

export default MealItem;