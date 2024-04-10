'use client'
import React, {useEffect, useState} from 'react';
import {UserProfile} from "@/types";
import {Badge} from "@/components/ui/badge";
import {Card, CardDescription, CardHeader, CardTitle} from "@/components/ui/card";
import {fetchProfileData, updateUserProfile} from "@/lib/data";
import {useSession} from "next-auth/react";

const ProfileFoodPreferences: React.FC = () => {
    const [preferences, setPreferences] = useState<UserProfile | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);
    // Get the user ID from the session
    const {data: session} = useSession();
    // Print the session object to the console
    const userId = session?.user.id;
    if (!userId) {
        throw new Error("User ID not found in session");
    }

    const fetchUserData = async () => {
        try {
            const data = await fetchProfileData(userId);
            setPreferences(data);
        } catch (err: any) {
            if (err.name === 'UserNotFoundError') {
                await createNewUserProfile();
            } else {
                setError(err);
            }
        } finally {
            setLoading(false);
        }
    };

    const createNewUserProfile = async () => {
        try {
            const data = await updateUserProfile(userId, {
                userId: userId,
                firstName: 'John',
                lastName: 'Doe',
                portionSize: 'medium',
                allergies: [],
                diet: [],
            });
            setPreferences(data);
        } catch (err: any) {
            setError(err);
        }
    };

    useEffect(() => {
        fetchUserData();
    }, [userId]);

    if (loading || !preferences) {
        return <div>Loading...</div>;
    }


    return (
        <div className="space-y-4">
            <p className="text-gray-500">Your food preferences</p>
            <div className="mt-4 space-y-4">
                <Card>
                    <CardHeader>
                        <CardTitle>Food Preferences</CardTitle>
                    </CardHeader>
                    <CardDescription>
                        Here are your food preferences and dietary restrictions:
                    </CardDescription>
                    <ul className="list-disc list-inside">
                        {preferences.diet && preferences.diet.map((diet, index) => (
                            <li key={index}>
                                <Badge>{diet}</Badge>
                            </li>
                        ))}
                    </ul>
                </Card>

                {/* allergies */}
                {preferences.allergies && preferences.allergies.length > 0 && (
                    <Card>
                        <CardHeader>
                            <CardTitle>allergies</CardTitle>
                        </CardHeader>
                        <CardDescription>
                            You have listed the following allergies:
                        </CardDescription>
                        <ul className="list-disc list-inside">
                            {preferences.allergies.map((allergy, index) => (
                                <li key={index}>
                                    <Badge>{allergy}</Badge>
                                </li>
                            ))}
                        </ul>
                    </Card>
                )}
            </div>
        </div>
    );
};

export default ProfileFoodPreferences;
