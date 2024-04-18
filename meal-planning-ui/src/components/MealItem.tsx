import React, {useState} from 'react';
import {Meal, MealType, ScheduledMeal} from "@/types";
import Image from "next/image";
import {ArrowPathIcon} from "@heroicons/react/24/outline";
import ChangeMealDrawer from "@/components/ChangeMealDrawer";
import LasagnaImage from 'public/meals/lasagna.webp';
import ChickenStirfryImage from 'public/meals/chickenstirfry.webp';
import SaladImage from 'public/meals/salad.webp';
import TacoImage from 'public/meals/tacos.webp';
import MealDetailDialog from "@/components/dialogs/MealDetailDialog";

interface MealItemProps {
    meal: MealType;
}

function MealItem({meal}: MealItemProps) {
    const [selectedMeal, setSelectedMeal] = useState<MealType | null>(null);
    const [isDrawerOpen, setDrawerOpen] = useState(false);
    const [isRefreshing, setRefreshing] = useState(false);

    const [isDialogOpen, setDialogOpen] = useState(false);

    if (meal.image_url === undefined) {
        // Choose a random image for the meal
        const images = [LasagnaImage, ChickenStirfryImage, SaladImage, TacoImage];
        meal.image_url = images[Math.floor(Math.random() * images.length)];
    }

    const handleRefresh = () => {
        setSelectedMeal(meal);
        setDrawerOpen(true);
    }

    // Parse the date string and get the day of the week
    const date = new Date(meal.date);
    const options: Intl.DateTimeFormatOptions = { weekday: 'long' };
    const dayOfWeek = new Intl.DateTimeFormat('en-US', options).format(date);


    return (
        <div className="relative">
            <MealDetailDialog meal={meal} onClose={() => setDialogOpen(false)} open={isDialogOpen}/>
            <div className={`opacity-${isRefreshing ? '50' : '100'}`}>
                <div className="w-full h-40 md:mb-6 rounded-full relative">
                    <div className="cursor-pointer relative filter hover:brightness-90" onClick={() => setDialogOpen(true)}>
                        <Image
                            src={meal.image_url}
                            alt={meal.name}
                            className="rounded-md"
                        />
                        <div
                            className="absolute inset-0 bg-black bg-opacity-50 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                            <ArrowPathIcon
                                className="h-5 w-5 text-gray-700 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"/>
                        </div>
                    </div>
                    {isRefreshing && (
                        <div className="absolute inset-0 flex items-center justify-center">
                            <div role="status">
                                <svg aria-hidden="true" className="w-8 h-8 text-primary animate-spin fill-primary"
                                     viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path
                                        d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                                    <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                                </svg>
                                <span className="sr-only">Loading...</span>
                            </div>
                        </div>
                    )}
                </div>
                <div className="pt-4 lg:py-8">
                    <div className="justify-between flex">
                        <h3 className="font-semibold text-lg">{dayOfWeek}</h3>
                        <button
                            onClick={handleRefresh}
                            className="inline-flex items-center"
                            aria-label="Refresh Meal">
                            <ArrowPathIcon className="h-5 w-5 text-gray-700"/>
                        </button>
                    </div>
                    <p className="text-gray-700">{meal.name}</p>
                </div>
            </div>
            <ChangeMealDrawer isDrawerOpen={isDrawerOpen} setDrawerOpen={setDrawerOpen} selectedMeal={selectedMeal} setRefreshing={setRefreshing} isRefreshing={isRefreshing}/>
        </div>
    );
}

export default MealItem;