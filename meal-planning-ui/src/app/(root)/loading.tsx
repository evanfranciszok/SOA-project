// app/(root)/loading.tsx
import MealScheduleSkeleton from "@/components/skeletons/MealScheduleSkeleton";
import Container from "@/components/Container";
import React from "react";
import PageTitle from "@/components/PageTitle";

export default function Loading() {
    return (
        <>
            <Container>
                <PageTitle title="Meal Schedule" description="Your weekly meal schedule"/>
                <MealScheduleSkeleton/>
            </Container>
        </>
    );
}
