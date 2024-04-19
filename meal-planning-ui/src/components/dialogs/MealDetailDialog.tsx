import {
    Dialog, DialogClose,
    DialogContent,
    DialogDescription, DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger
} from "@/components/ui/dialog";
import { MealType } from "@/types";
import { Button } from "@/components/ui/button";
import { PencilIcon } from "@heroicons/react/16/solid";
import { Badge } from "@/components/ui/badge";
import React from "react";
import Image from "next/image";
import ChickenStirfryImage from "public/meals/chickenstirfry.webp";

interface MealDetailProps {
    meal: MealType;
    onClose: () => void;
    open: boolean;
}

export default function MealDetailDialog({ meal, onClose, open }: MealDetailProps) {
    if (meal.image_url === undefined) {
        meal.image_url = ChickenStirfryImage
    }
    return (
        <Dialog open={open}>
            <DialogContent className="rounded-lg bg-white shadow-xl p-6 transition-all transform">
                <DialogHeader className="border-b pb-4">
                    <DialogTitle className="text-xl font-semibold text-gray-800">{meal.name}</DialogTitle>
                    <DialogDescription className="text-sm text-gray-500 mt-1">
                        Meal for {meal.date}
                    </DialogDescription>
                </DialogHeader>
                {/*<Image src={meal.image_url} alt={meal.name} width={300} height={300} className="rounded-md mt-4"/>*/}
                {/* Add fake instructions */}
                <div className="mt-4">
                    <h2 className="text-lg font-semibold">Instructions</h2>
                    <p className="text-gray-700 text-sm mt-2">
                {/*        Think of random instructions */}
                        1. Preheat oven to 350 degrees F (175 degrees C).<br/>
                        2. In a large skillet, cook and stir beef over medium heat until brown. Drain.<br/>
                        3. In a large bowl, combine the beef, salsa, and taco seasoning mix.<br/>
                        4. Place a tortilla in a greased 9-in. pie plate. Layer with a fourth of the meat mixture and a fourth of the cheese. Repeat layers three times. Top with remaining tortilla.<br/>
                        5. Bake at 350 degrees F (175 degrees C) for 15-20 minutes or until heated through. Let stand for 5 minutes before cutting.
                    </p>
                </div>

                <div className="mt-4">
                    <h2 className="text-lg font-semibold">Ingredients</h2>
                </div>
                <div className="flex flex-wrap gap-2 mt-1">
                    {meal.ingredients.map((ingredient, index) => (
                        <Badge key={index} className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-xs">
                            {ingredient}
                        </Badge>
                    ))}
                </div>

                <DialogFooter className="mt-4 flex justify-end space-x-2">
                    <DialogClose asChild>
                        <Button variant="outline" onClick={onClose} className="px-4 py-2 border border-gray-300 text-gray-700 hover:bg-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-150 ease-in-out">
                            Close
                        </Button>
                    </DialogClose>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}
