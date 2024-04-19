'use client'
import React, {useEffect, useState} from 'react';
import {UserProfile} from "@/types";
import {Badge} from "@/components/ui/badge";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card";
import {fetchProfileData, refreshMeals, updateUserProfile} from "@/lib/data";
import {useSession} from "next-auth/react";
import {UserNotFoundError} from "@/lib/exceptions";
import UpdatePreferencesDialog from "@/components/dialogs/UpdatePreferencesDialog";
import UpdateAllergiesDialog from "@/components/dialogs/UpdateAllergiesDialog";
import {toast} from "sonner"

const ProfileFoodPreferences: React.FC = () => {
    const [preferences, setPreferences] = useState<UserProfile | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);
    const [showFoodPreferencesDialog, setShowFoodPreferencesDialog] = useState(false);

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
            if (err instanceof UserNotFoundError) {
                // Handle user not found error
                console.error("User not found");
            } else {
                setError(err);
            }
        } finally {
            setLoading(false);
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
            <div className="mt-4 space-y-3 md:space-y-0 grid grid-cols-1 md:grid-cols-2 md:gap-x-2">
                <Card>
                    <CardHeader>
                        <CardTitle className="flex justify-between">
                            <span>Food preferences</span>
                            <UpdatePreferencesDialog onClose={() => setShowFoodPreferencesDialog(false)}
                                                     currentPreferences={preferences.diet} onSave={(newPreferences) => {
                                toast.success("Food preferences updated successfully");
                                toast.info("Refreshing meals...");
                                refreshMeals(userId).then(r => console.log(r));
                                updateUserProfile(userId, {
                                    ...preferences,
                                    diet: newPreferences,
                                }).then(r => setPreferences(r));
                                setShowFoodPreferencesDialog(false);
                                setShowFoodPreferencesDialog(false);
                            }
                            }/>
                        </CardTitle>
                    </CardHeader>
                    <CardContent>
                        <CardDescription>
                            Here are your dietary restrictions:
                        </CardDescription>
                        <ul className="list-none flex flex-wrap">
                            {preferences.diet && preferences.diet.length > 0 && preferences.diet.map((diet, index) => (
                                <li key={index} className="mt-2 mr-2">
                                    <Badge
                                        className="inline-flex items-center justify-center px-3 py-1 text-sm font-medium leading-none text-white rounded-full hover:bg-gray-300 hover:text-black">
                                        {diet}
                                    </Badge>
                                </li>
                            ))}
                        </ul>
                    </CardContent>
                </Card>

                {/* allergies */}
                {preferences.allergies && (
                    <Card>
                        <CardHeader>
                            <CardTitle className="flex justify-between">
                                <span>Allergies</span>
                                <UpdateAllergiesDialog onClose={() => setShowFoodPreferencesDialog(false)}
                                                       currentAllergies={preferences.allergies}
                                                       onSave={(newPreferences) => {
                                                           toast.success("Allergies updated successfully");
                                                           toast.info("Refreshing meals...");
                                                           refreshMeals(userId).then(r => console.log(r));
                                                           updateUserProfile(userId, {
                                                               ...preferences,
                                                               allergies: newPreferences,
                                                           }).then(r => setPreferences(r));
                                                           setShowFoodPreferencesDialog(false);
                                                       }
                                                       }/>
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription>
                                Here are your allergies:
                            </CardDescription>
                            <ul className="list-none flex flex-wrap">
                                {preferences.allergies && preferences.allergies.length > 0 && preferences.allergies.map((allergy, index) => (
                                    <li key={index} className="mt-2 mr-2">
                                        <Badge
                                            className="inline-flex items-center justify-center px-3 py-1 text-sm font-medium leading-none bg-red-400 border-red-500 border-2 text-white rounded-full hover:bg-gray-100 hover:text-black">
                                            {allergy}
                                        </Badge>
                                    </li>
                                ))}
                            </ul>
                        </CardContent>
                    </Card>
                )}
            </div>
        </div>
    );
};

export default ProfileFoodPreferences;
