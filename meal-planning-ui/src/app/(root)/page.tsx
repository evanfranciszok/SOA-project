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
    const { data: session, status } = useSession()


    const router = useRouter();

    if (status === "loading") {
        return <p>Loading...</p>;
    }

    if (!session) {
        router.push("api/auth/signin");
        // You could also display a loading message while the user is redirected
        // to the login page.
        return null;
    }


    // Add 5 seconds delay to simulate loading
    return (
        <>
            <Container>
                <PageTitle title="Meal Schedule" description="Your weekly meal schedule"/>
                <MealSchedule/>
            </Container>
        </>
    );
}
