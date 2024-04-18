'use client'
import Container from "@/components/Container";
import PageTitle from "@/components/PageTitle";
import React, {useEffect, useState} from "react";
import {InventoryItem, InventoryResponse} from "@/types";
import {fetchInventory, updateInventory} from "@/app/inventory/actions";
import {Button} from "@/components/ui/button";
import {PlusIcon} from "@heroicons/react/24/outline";
import {Input} from "@/components/ui/input";
import {format} from 'date-fns';
import {toast} from "sonner";
import {useSession} from "next-auth/react";


export default function Inventory() {
    const [inventory, setInventory] = useState<InventoryResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);
    const [newItemName, setNewItemName] = useState('');

    const { data: session, status } = useSession();

    // Ensure data fetching happens client-side and only when the session is available
    useEffect(() => {
        if (status === 'authenticated') {
            const userId = session?.user?.id;
            if (!userId) {
                setError(new Error("User ID not found in session"));
                return;
            }

            fetchInventory(userId)
                .then(data => {
                    setInventory(data);
                    setLoading(false);
                })
                .catch(error => {
                    console.error("Failed to fetch inventory:", error);
                    setError(error);
                    setLoading(false);
                });
        }
    }, [session, status]);

    function addItemToInventory() {
        //     Take the current inventory and add the new item
        if (!newItemName) return; // Don't add item if input is empty

        const newItem: InventoryItem = {
            name: newItemName,
            quantity: 1,
            expiry_date: format(new Date(new Date().setDate(new Date().getDate() + 1)), 'yyyy-MM-dd'),
        };

        if (!inventory) {
            //     Create empty inventory if it doesn't exist
            let newInventory = {
                userId: session?.user?.id,
                food_inventory: [
                    newItem,
                ],
            }
            return;
        }
        let newInventory = inventory.food_inventory;
        if (!newInventory) {
            newInventory = [];
        }
        newInventory.push(newItem);
        setInventory({
            ...inventory,
            food_inventory: newInventory,
        });
        // const newInventory = inventory.food_inventory.concat(item);

        updateInventory(inventory).then(r =>
                toast.success(newItemName + " added to inventory")
            // console.log("Inventory updated")
        ).catch(e => {
                console.error("Failed to update inventory:", e);
                setError(e);
            }
        );
    }

    function removeItemFromInventory(itemToRemove: InventoryItem) {
        if (!inventory || !inventory.food_inventory) return;

        // Remove the item from the local state
        const newInventory = inventory.food_inventory.filter(item => item !== itemToRemove);
        setInventory({
            ...inventory,
            food_inventory: newInventory,
        });

        // Update the server-side inventory
        updateInventory(inventory).then(() =>
            toast.success(itemToRemove.name + " removed from inventory")
            // console.log("Inventory updated")
        ).catch(e => {
                console.error("Failed to update inventory:", e);
                setError(e);
            }
        );
    }

    const addInventoryItemNode = () => {
        return (
            <form onSubmit={(e) => {
                e.preventDefault();
                addItemToInventory();
            }} className="flex space-x-2">
                <Input
                    type="text"
                    placeholder="Add inventory item"
                    value={newItemName}
                    onChange={(e) => setNewItemName(e.target.value)}
                />
                <Button className='hover:bg-primary/90' type="submit">
                    <PlusIcon className="h-6 w-6"/>
                </Button>
            </form>
        )
    }
    return (
        <Container>
            <PageTitle title="Inventory" description="Manage your inventory here." button={addInventoryItemNode()}/>
            {loading && <p>Loading...</p>}
            {error && <p>Error: {error.message}</p>}
            {inventory && (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
                    {inventory.food_inventory && inventory.food_inventory.map(item => (
                        <div key={item.name}
                             className="bg-white rounded-lg shadow hover:shadow-lg transition-shadow duration-300 p-4">
                            <div className="flex justify-between items-center">
                                <h3 className="text-lg font-semibold">{item.name}</h3>
                                <Button variant='destructive' onClick={() => removeItemFromInventory(item)}>X</Button>
                            </div>
                            <p className="text-gray-600">Quantity: {item.quantity}</p>
                            <p className="text-gray-600">Expiry: {item.expiry_date}</p>
                        </div>
                    ))}
                </div>
            )}
        </Container>
    );
}