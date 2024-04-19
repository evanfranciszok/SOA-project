import * as React from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ShoppingListPerStore } from "@/types";
import {Badge} from "@/components/ui/badge";

interface ShoppingListCardProps {
    shoppingList: ShoppingListPerStore;
}

export default function ShoppingListCard({ shoppingList }: ShoppingListCardProps) {
    return (
        <Card className="max-w-md bg-white shadow-lg rounded-lg overflow-hidden">
            <CardHeader className="rounded-t-lg border-primary border-4 text-white p-4">
                <CardTitle className="text-primary text-lg font-bold ">Shopping list for {shoppingList.storeId}</CardTitle>
                <CardDescription className="text-sm">What you need to pick up:</CardDescription>
            </CardHeader>
            <CardContent className="p-4">
                {/*<div className="flex flex-wrap gap-2 mt-1">
                    {meal.ingredients.map((ingredient, index) => (
                        <Badge key={index} className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-xs">
                            {ingredient}
                        </Badge>
                    ))}
                </div>*/}
                <div className="flex flex-wrap gap-2 mt-1">
                    {shoppingList.ingredients.map((ingredient, index) => (
                        <Badge key={index} className="bg-accent text-primary px-3 py-1 rounded-full text-xs">
                            {ingredient}
                        </Badge>
                    ))}
                </div>
            </CardContent>
            {/*<CardFooter className="flex justify-between items-center p-4 bg-gray-100">*/}
            {/*    <Button variant="outline" className="text-gray-600 border-gray-400 hover:bg-gray-200">Clear</Button>*/}
            {/*    <Button className="bg-blue-500 hover:bg-blue-600 text-white">Save</Button>*/}
            {/*</CardFooter>*/}
        </Card>
    );
}
