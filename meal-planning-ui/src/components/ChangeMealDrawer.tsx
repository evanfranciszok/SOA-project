// components/ChangeMealDrawer.tsx
'use client'
import React from 'react';
import {
    Drawer,
    DrawerClose,
    DrawerContent,
    DrawerDescription,
    DrawerFooter,
    DrawerHeader,
    DrawerTitle,
} from "@/components/ui/drawer"

import {Button} from "@/components/ui/button";
import {ScheduledMeal} from "@/types";
// Props for the ChangeMealDrawer component
// The meal is a prop that is passed to the component
interface ChangeMealDrawerProps {
    selectedMeal: ScheduledMeal | null;
    isDrawerOpen: boolean;
    setDrawerOpen: (isOpen: boolean) => void;
    isRefreshing: boolean;
    setRefreshing: (isRefreshing: boolean) => void;
}

export default function ChangeMealDrawer({selectedMeal, isDrawerOpen, setDrawerOpen, setRefreshing}: ChangeMealDrawerProps) {
    const handleReasonSelect = (reason: string) => {
        console.log(`Reason for changing meal: ${reason}`);
        // Here you can handle the logic for suggesting a new meal based on the selected reason
        setDrawerOpen(false);
        // Set the refreshing state to true to indicate that the meal is being refreshed
        setRefreshing(true);
    };
    if (!selectedMeal) {
        return null;
    }
    return (
        <Drawer open={isDrawerOpen} onOpenChange={setDrawerOpen}>
            {/*<DrawerTrigger>Open</DrawerTrigger>*/}
            <DrawerContent style={{paddingBottom: 'env(safe-area-inset-bottom)'}}>
                <div className="mx-auto w-full max-w-sm">
                    <DrawerHeader>
                        <DrawerTitle>Change Meal</DrawerTitle>
                        <DrawerDescription>
                            {selectedMeal.day} - {selectedMeal.name}
                        </DrawerDescription>
                    </DrawerHeader>
                    <div className="flex flex-col items-center p-4">
                        <h2 className="text-xl font-semibold mb-4">Why change the meal?</h2>
                        <div className="grid gap-2 grid-cols-3">
                            <Button variant="default" className="bg-green-500"
                                    onClick={() => handleReasonSelect('Too easy')}>Too easy</Button>
                            <Button variant="default" className="bg-red-500"
                                    onClick={() => handleReasonSelect('Don&apos;t like it')}>Don&apos;t like it</Button>
                            <Button variant="default" className="bg-yellow-500"
                                    onClick={() => handleReasonSelect('Too hard')}>Too hard</Button>
                        </div>
                    </div>
                    <DrawerFooter>
                        {/* Additional footer items if needed */}
                        <DrawerClose onClick={() => setDrawerOpen(false)} asChild>
                            <Button variant="outline">Close</Button>
                        </DrawerClose>
                    </DrawerFooter>
                </div>
            </DrawerContent>

        </Drawer>
    )
}