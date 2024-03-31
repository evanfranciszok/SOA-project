// components/skeletons/MealScheduleSkeleton.tsx
import React from 'react';
import MealItemSkeleton from "@/components/skeletons/MealItemSkeleton"; // Adjust the import path as necessary

const MealScheduleSkeleton = () => {
    // Define how many skeleton items you want to display, e.g., matching the expected number of meals.
    const skeletonItems = [1, 2, 3, 4]; // For Monday to Thursday

    return (
        <div className="py-6">
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                {skeletonItems.map((item) => (
                    <MealItemSkeleton key={item} />
                ))}
            </div>
        </div>
    );
};

export default MealScheduleSkeleton;
