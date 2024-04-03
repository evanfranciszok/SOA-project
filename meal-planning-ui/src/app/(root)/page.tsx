// app/(root)/page.tsx
import MealSchedule from "@/components/MealSchedule";
import Container from "@/components/Container";
import React from "react";
import PageTitle from "@/components/PageTitle";

export default function Home() {
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