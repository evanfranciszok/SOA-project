// src/app/groceries/page.tsx
'use client'
import PageTitle from "@/components/PageTitle";
import Container from "@/components/Container";
import {useEffect, useState} from "react";
import {fetchShoppingList} from "@/app/groceries/actions";
import {useSession} from "next-auth/react";
import {ShoppingList, ShoppingListPerStore} from "@/types";
import ShoppingListCard from "@/components/ShoppingListCard";

export default function Groceries() {
    const [shoppingList, setShoppingList] = useState<ShoppingList | null>(null);
    const { data: session, status } = useSession();
    // Ensure data fetching happens client-side and only when the session is available
    useEffect(() => {
        if (status === 'authenticated') {
            const userId = session?.user?.id;
            if (!userId) {
                return;
            }
            fetchShoppingList(userId).then(data => {
                setShoppingList(data);
            })
        }
    }, [session, status]);


    return (
        <Container>
            <PageTitle title="Groceries" description="Your groceries list for different supermarkets"/>

            <div className="grid gap-4 grid-cols-1 md:grid-cols-2 mt-2">
                {shoppingList && shoppingList.shoppingListPerStore.map((list: ShoppingListPerStore) => (
                    <ShoppingListCard key={list.storeId} shoppingList={list} />
                ))}
            </div>
        </Container>
    );
}