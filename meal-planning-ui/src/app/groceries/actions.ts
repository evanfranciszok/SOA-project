// src/app/groceries/actions.ts
'use server'

import { ShoppingList} from "@/types";

export async function fetchShoppingList(userId: string): Promise<ShoppingList> {
    const apiBaseUrl = process.env.SHOPPING_LIST_API_URL as string
    return fetch(`${apiBaseUrl}/shoppingLists?userId=${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch shopping list for user ${userId}`);
            }
            return response.json();
        });
}