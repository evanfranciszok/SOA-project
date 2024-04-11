'use client'
import Container from "@/components/Container";
import PageTitle from "@/components/PageTitle";
import React, {useEffect, useState} from "react";
import {InventoryResponse} from "@/types";
import {fetchInventory} from "@/app/inventory/actions";

export default function Inventory() {
    const [inventory, setInventory] = useState<InventoryResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    const [userId, setUserId] = useState('1');
    useEffect(() => {
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
    }, [userId]);

    return (
        <Container>
            <PageTitle title="Inventory" description="Manage your inventory here."/>
            {loading && <p>Loading...</p>}
            {error && <p>Error: {error.message}</p>}
            {inventory && (
                <ul>
                    {inventory.food_inventory.map(item => (
                        <li key={item.name}>
                            {item.name} - {item.quantity} - {item.expiry_date}
                        </li>
                    ))}
                </ul>
            )}
        </Container>
    );
}