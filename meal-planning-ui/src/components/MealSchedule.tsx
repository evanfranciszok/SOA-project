'use client'
import React, {useEffect, useState} from 'react';
import {MealType, ScheduledMeal} from '@/types';
import MealItem from "@/components/MealItem"; // Assume this is the correct import path
import LasagnaImage from '../../public/meals/lasagna.webp';
import TacoImage from '../../public/meals/tacos.webp';
import ChickenStirFryImage from '../../public/meals/chickenstirfry.webp';
import SaladImage from '../../public/meals/salad.webp';
import {fetchMeals, refreshMeals} from "@/lib/data";
import {useSession} from "next-auth/react";
import {toast} from "sonner";
import {Button} from "@/components/ui/button";
import PageTitle from "@/components/PageTitle";
import { ArrowPathIcon} from "@heroicons/react/24/outline";

//

async function MealSchedule() {
    // const meals: ScheduledMeal[] = [
    //     {
    //         id: 1,
    //         day: 'Monday',
    //         name: 'Lasagna Bolognese',
    //         image_url: LasagnaImage,
    //     },
    //     {
    //         id: 2,
    //         day: 'Tuesday',
    //         name: 'Real good Tacos',
    //         image_url: TacoImage,
    //     },
    //     {
    //         id: 3,
    //         day: 'Wednesday',
    //         name: 'Chicken Stir Fry',
    //         image_url: ChickenStirFryImage,
    //     },
    //     {
    //         id: 4,
    //         day: 'Thursday',
    //         name: 'Salad',
    //         image_url: SaladImage,
    //     },
    // ];
    const [meals, setMeals] = useState<MealType[]>([]);

    // Get the user ID from the session
    const {data: session} = useSession();
    // Print the session object to the console
    const userId = session?.user.id;
    if (!userId) {
        throw new Error("User ID not found in session");
    }

    useEffect(() => {
        // Assume the user id is available in the session
        fetchMeals(userId)
            .then(data => setMeals(data))
            .catch(error => console.error(error));
    }, []);

    const refreshAllMeals = async () => {
        await refreshMeals(userId);
        toast.info("Refreshing meals...");

        // Wait 5 seconds before notifying the user that the refresh is halfway done
        setTimeout(() => toast.info("Refresh is 50% complete..."), 5000);

        // Wait 10 seconds before fetching the meals again
        setTimeout(() => {
            fetchMeals(userId)
                .then(data => {
                        setMeals(data);
                        toast.success("Meals refreshed successfully");
                    }
                )
                .catch(error => console.error(error));
        }, 10000);
    };

    const refreshButton =  <Button className={'ml-auto'} onClick={refreshAllMeals}><ArrowPathIcon className={'h-5 w-5 mr-2'}/> Refresh</Button>;

    return (
        <div>
            <PageTitle title="Meal Schedule" description="Your weekly meal schedule" button={refreshButton}/>
            <div className="py-6">
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                    {meals.map((meal) => (
                        <MealItem key={meal.id} meal={meal}/>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default MealSchedule;