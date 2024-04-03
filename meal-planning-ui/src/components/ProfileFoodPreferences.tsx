// components/ProfileFoodPreferences.tsx
import React from 'react';
import {UserProfile} from "@/types";
import {Badge} from "@/components/ui/badge";
import {Card, CardDescription, CardHeader, CardTitle} from "@/components/ui/card";
import {fetchProfileData} from "@/lib/data";

async function ProfileFoodPreferences() {
    const preferences = await fetchProfileData();
    // console.log(preferences);
    if (!preferences) {
        return <div>Loading screen...</div>;
    }
    return (
        <div>
            <p className="text-gray-500">Your food preferences</p>
            <div className="mt-4">
                {/* Example of displaying vegetarian preference */}
                <Card>
                    <CardHeader>
                        <CardTitle>Food Preferences</CardTitle>
                        <CardDescription>
                            You prefer {preferences.isVegetarian ? 'vegetarian' : 'non-vegetarian'} meals
                            <Badge className="ml-auto">{preferences.isVegetarian ? 'Vegetarian' : 'Non-vegetarian'}</Badge>
                        </CardDescription>
                    </CardHeader>
                </Card>
                {/* Add more preferences display here */}
                {/*    Allergies    */}
            </div>
        </div>

    );
}

export default ProfileFoodPreferences;
