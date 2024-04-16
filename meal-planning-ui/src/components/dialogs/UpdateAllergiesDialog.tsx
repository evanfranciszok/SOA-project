// src/components/UpdateAllergiesDialog.tsx

import React, { Fragment, useEffect, useState } from 'react';
import { Dialog, DialogClose, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogFooter, DialogTrigger } from '../ui/dialog';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Combobox, Transition } from "@headlessui/react";
import { CheckIcon, ChevronUpDownIcon, PencilIcon } from "@heroicons/react/16/solid";
import { fetchFoodItems } from "@/lib/data";
import { FoodItem } from "@/types";
import {Badge} from "@/components/ui/badge";

type FoodItemComboboxProps = {
    possibleFoodItems: FoodItem[];
    setNewAllergy: (value: string) => void;
};

function FoodItemCombobox({ possibleFoodItems, setNewAllergy }: FoodItemComboboxProps) {
    const [selected, setSelected] = useState<FoodItem | null>(null);
    const [query, setQuery] = useState('');

    console.log(possibleFoodItems);

    const filteredOptions = query === '' || !Array.isArray(possibleFoodItems)
        ? possibleFoodItems
        : possibleFoodItems.filter((item) =>
            item.name.toLowerCase().includes(query.toLowerCase())
        );


    useEffect(() => {
        if (selected) {
            setNewAllergy(selected.name);
        }
    }, [selected, setNewAllergy]);

    return (
        <Combobox value={selected} onChange={setSelected}>
            <div className="relative mt-1">
                <Combobox.Input
                    className="w-full border-none py-2 pl-3 pr-10 text-sm leading-5 text-gray-900 focus:ring-0"
                    displayValue={(item: FoodItem) => item ? item.name : ''}
                    onChange={(event) => setQuery(event.target.value)}
                    placeholder="Search food items"
                />
                <Combobox.Button className="absolute inset-y-0 right-0 flex items-center pr-2">
                    <ChevronUpDownIcon className="h-5 w-5 text-gray-400" aria-hidden="true" />
                </Combobox.Button>
            </div>
            <Transition
                as={Fragment}
                leave="transition ease-in duration-100"
                leaveFrom="opacity-100"
                leaveTo="opacity-0"
            >
                <Combobox.Options className="absolute mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black/5 focus:outline-none sm:text-sm">
                    {filteredOptions.length === 0 && query !== '' ? (
                        <div className="relative cursor-default select-none px-4 py-2 text-gray-700">
                            Nothing found.
                        </div>
                    ) : (
                        filteredOptions.map((item) => (
                            <Combobox.Option
                                key={item.id}
                                value={item}
                                className={({ active }) =>
                                    `relative cursor-default select-none py-2 pl-3 pr-4 ${
                                        active ? 'bg-teal-600 text-white' : 'text-gray-900'
                                    }`
                                }
                            >
                                {({ selected, active }) => (
                                    <>
                                        <span className={`block truncate ${selected ? 'font-medium' : 'font-normal'}`}>
                                            {item.name} ({item.group})
                                        </span>
                                        {selected && (
                                            <span className={`absolute inset-y-0 left-0 flex items-center pl-3 ${active ? 'text-white' : 'text-teal-600'}`}>
                                                <CheckIcon className="h-5 w-5" aria-hidden="true" />
                                            </span>
                                        )}
                                    </>
                                )}
                            </Combobox.Option>
                        ))
                    )}
                </Combobox.Options>
            </Transition>
        </Combobox>
    );
}

type UpdateAllergiesDialogProps = {
    onClose: () => void;
    currentAllergies: string[] | undefined;
    onSave: (allergies: string[]) => void;
};

const UpdateAllergiesDialog = ({ onClose, currentAllergies, onSave }: UpdateAllergiesDialogProps) => {
    const [allergies, setAllergies] = useState<string[]>(currentAllergies || []);
    const [newAllergy, setNewAllergy] = useState('');
    const [possibleFoodItems, setPossibleFoodItems] = useState<FoodItem[]>([]);

    const addAllergy = () => {
        if (newAllergy && !allergies.includes(newAllergy)) {
            setAllergies([...allergies, newAllergy]);
            setNewAllergy('');
        }
    };

    const removeAllergy = (allergy: string) => {
        setAllergies(allergies.filter((a) => a !== allergy));
    };

    const handleSave = () => {
        onSave(allergies);
        onClose();
    };

    useEffect(() => {
        fetchFoodItems().then(setPossibleFoodItems).catch(console.error);

    }, []);
    console.log(possibleFoodItems);

    return (
        <Dialog>
            <DialogTrigger asChild>
                <Button variant="outline">
                    <PencilIcon className="h-5 w-5 mr-2"/> Edit Allergies
                </Button>
            </DialogTrigger>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Update Allergy Information</DialogTitle>
                    <DialogDescription>
                        Select your allergies from the list of food items
                    </DialogDescription>
                </DialogHeader>
                <div className="flex flex-wrap gap-2">
                    {allergies.map((allergy, index) => (
                        <Badge key={index} onDismiss={() => removeAllergy(allergy)}>
                            {allergy}
                        </Badge>
                    ))}
                </div>
                <div className="mt-4">
                    <FoodItemCombobox possibleFoodItems={possibleFoodItems} setNewAllergy={setNewAllergy} />
                    <Button className="mt-2" onClick={addAllergy}>Add Allergy</Button>
                </div>
                <DialogFooter>
                    <DialogClose asChild>
                        <Button variant="outline" onClick={handleSave}>Save</Button>
                    </DialogClose>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
};

export default UpdateAllergiesDialog;
