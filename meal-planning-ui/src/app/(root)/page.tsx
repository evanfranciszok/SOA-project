// app/(root)/page.tsx
'use client'
import MealSchedule from "@/components/MealSchedule";
import Container from "@/components/Container";
import React from "react";
import PageTitle from "@/components/PageTitle";
import {getServerSession} from "next-auth";
import { useRouter } from "next/navigation";
import {useSession, signIn} from "next-auth/react";
export default function Home() {
    // Add 5 seconds delay to simulate loading
    const {data: session, status} = useSession();
    // Check if user is logged in, if not, show the anonymous page
    if (status === 'loading') {
        return <div>Loading...</div>
    }
    if (!session?.user) {
        return (
            <Container>
                <PageTitle title="Meal Schedule" description="Your weekly meal schedule"/>
                <p>You need to sign in to view this page</p>
                <button onClick={() => signIn()}>Sign in</button>
            </Container>
        )
    }

    return (
        <>
            <Container>
                <PageTitle title="Meal Schedule" description="Your weekly meal schedule"/>
                <MealSchedule/>
            </Container>
        </>
    );
}
