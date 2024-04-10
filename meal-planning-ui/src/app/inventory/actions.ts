'use server'
import {InventoryResponse} from "@/types";
export async function fetchInventory(userId: string): Promise<InventoryResponse> {
    try {
        const response = await fetch(`http://inventorymanagement:8080/${userId}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        // Print the response to the console
        console.log(response);
        return await response.json();
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
        throw error; // Re-throw to handle it in the calling component
    }
}
