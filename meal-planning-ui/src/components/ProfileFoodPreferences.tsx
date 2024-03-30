// components/ProfileFoodPreferences.tsx

import React from 'react';

const ProfileFoodPreferences = () => (
    <div>
        {/*<h1 className="text-xl font-semibold mt-2">Food Preferences</h1>*/}
        <p className="text-gray-500">Your food preferences</p>
        <div className="mt-4">
            <div className="flex items-center justify-between">
                <div className="flex items-center">
                    <img src="https://via.placeholder.com/150" alt="Food" className="h-16 w-16 rounded-full"/>
                    <div className="ml-4">
                        <h2 className="text-lg font-semibold">Vegetarian</h2>
                        <p className="text-gray-500">You prefer vegetarian meals</p>
                    </div>
                </div>
                <button className="btn btn-primary">Edit</button>
            </div>
            <div className="flex items-center justify-between mt-4">
                <div className="flex items-center">
                    <img src="https://via.placeholder.com/150" alt="Food" className="h-16 w-16 rounded-full"/>
                    <div className="ml-4">
                        <h2 className="text-lg font-semibold">Vegan</h2>
                        <p className="text-gray-500">You prefer vegan meals</p>
                    </div>
                </div>
                <button className="btn btn-primary">Edit</button>
            </div>
        </div>
    </div>
);

export default ProfileFoodPreferences;