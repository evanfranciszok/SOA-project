// app/profile/page.tsx
'use client'
import React from 'react';
import Container from "@/components/Container";
import PageTitle from "@/components/PageTitle";
import {
    Tabs,
    TabsContent,
    TabsList,
    TabsTrigger,
} from "@/components/ui/tabs"
import ProfileFoodPreferences from "@/components/ProfileFoodPreferences";
import ProfileGeneralPreferences from "@/components/ProfileGeneralPreferences";
import {useSession} from "next-auth/react";
export const dynamic = "force-dynamic";
export const fetchCache = "force-no-store";

export default function Profile() {
    // Check if user has a session
    const {data: session, status} = useSession();
    // Check if user is logged in, if not, show the anonymous page
    if (status === 'loading') {
        return <div>Loading...</div>
    }
    return (
        <>
            <Container>
                <PageTitle title="Profile" description=""/>
                <Tabs defaultValue="foodpreferences" className="mt-4">
                    <TabsList className="grid w-full grid-cols-2">
                        <TabsTrigger value="foodpreferences">Food preferences</TabsTrigger>
                        <TabsTrigger value="general">Profile options</TabsTrigger>
                    </TabsList>
                    <TabsContent value="foodpreferences">
                        <ProfileFoodPreferences/>
                    </TabsContent>
                    <TabsContent value="general">
                        <ProfileGeneralPreferences/>
                    </TabsContent>
                </Tabs>
            </Container>
        </>
    );
}