// components/skeletons/MealItemSkeleton.tsx
import React from 'react';

const MealItemSkeleton = () => {
    return (
        <div className="animate-pulse max-w-4xl mx-auto p-4">
            <div className="mb-6 rounded-lg overflow-hidden">
                <div className="w-40 h-40 bg-gray-300" />
                <div className="pt-4 lg:py-8 px-4">
                    <div className="flex justify-between">
                        <div className="h-6 bg-gray-300 rounded w-1/4"></div>
                        <div className="h-6 bg-gray-300 rounded w-8"></div>
                    </div>
                    <div className="mt-2 h-6 bg-gray-300 rounded"></div>
                </div>
            </div>
        </div>
    );
};

export default MealItemSkeleton;
