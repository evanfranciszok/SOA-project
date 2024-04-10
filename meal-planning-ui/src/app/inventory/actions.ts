'use server'
import {InventoryResponse} from "@/types";
export async function fetchInventory(userId: string): Promise<InventoryResponse> {
    try {
        const apiBaseUrl = process.env.INVENTORY_API_URL as string
        // const response = await fetch(`http://inventorymanagement:8080/${userId}`);
        const response = await fetch(`${apiBaseUrl}/${userId}`);
        // Print the url to the console
        console.log(response.url);
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
