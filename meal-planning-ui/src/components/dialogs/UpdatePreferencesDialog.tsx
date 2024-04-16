// src/components/dialogs.tsx

import React, {Fragment, useEffect, useState} from 'react';
import {
    Dialog,
    DialogClose,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger
} from '../ui/dialog';
import {Button} from "@/components/ui/button";
import {Badge} from "@/components/ui/badge";
import {Input} from "@/components/ui/input";
import {fetchDietPreferences} from "@/lib/data";
import {DietPreference} from "@/types";
import {Combobox, Transition} from "@headlessui/react";
import {CheckIcon, ChevronUpDownIcon, PencilIcon} from "@heroicons/react/16/solid";


type DietPreferenceComboboxProps = {
    possibleDietPreferences: DietPreference[];
    setNewPreference: (value: string) => void;
}

function DietPreferenceCombobox({possibleDietPreferences, setNewPreference}: DietPreferenceComboboxProps) {
    const [selected, setSelected] = useState<DietPreference>();
    const [query, setQuery] = useState('');

    const filteredOptions = query === ''
        ? possibleDietPreferences
        : possibleDietPreferences.filter((option) =>
            option.diet.toLowerCase().includes(query.toLowerCase())
        );

    useEffect(() => {
        if (selected) {
            setNewPreference(selected.diet);
        }

    }, [selected, setNewPreference]);

    // const isSelected = (option: { diet: any; }) => selected.find((sel: any) => sel.diet === option.diet);

    return (
        <Combobox value={selected} onChange={setSelected}>
            <div className="relative mt-1">
                <Combobox.Input
                    className="w-full border-none py-2 pl-3 pr-10 text-sm leading-5 text-gray-900 focus:ring-0"
                    displayValue={(option: { diet: any; }) => option.diet}
                    onChange={(event) => setQuery(event.target.value)}
                    placeholder="Select dietary preferences"
                />
                <Combobox.Button className="absolute inset-y-0 right-0 flex items-center pr-2">
                    <ChevronUpDownIcon
                        className="h-5 w-5 text-gray-400"
                        aria-hidden="true"
                    />
                </Combobox.Button>
            </div>
            <Transition
                as={Fragment}
                leave="transition ease-in duration-100"
                leaveFrom="opacity-100"
                leaveTo="opacity-0"
            >
                <Combobox.Options
                    className="absolute mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black/5 focus:outline-none sm:text-sm">
                    {filteredOptions.length === 0 && query !== '' ? (
                        <div className="relative cursor-default select-none px-4 py-2 text-gray-700">
                            Nothing found.
                        </div>
                    ) : (
                        filteredOptions.map((option) => (
                            <Combobox.Option
                                key={option.id}
                                value={option}
                                className={({active}) =>
                                    `relative cursor-default select-none py-2 pl-3 pr-4 ${
                                        active ? 'bg-teal-600 text-white' : 'text-gray-900'
                                    }`
                                }
                            >
                                {({selected, active}) => (
                                    <>
                      <span
                          className={`block truncate ${
                              selected ? 'font-medium' : 'font-normal'
                          }`}
                      >
                        {option.diet} - {option.description}
                      </span>
                                        {selected ? (
                                            <span
                                                className={`absolute inset-y-0 left-0 flex items-center pl-3 ${
                                                    active ? 'text-white' : 'text-teal-600'
                                                }`}
                                            >
                          <CheckIcon className="h-5 w-5" aria-hidden="true"/>
                        </span>
                                        ) : null}
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

// Give the props a type to ensure the correct data is passed
type UpdatePreferencesDialogProps = {
    onClose: () => void;
    currentPreferences: string[] | undefined;
    onSave: (preferences: string[]) => void;
};
const UpdatePreferencesDialog = ({onClose, currentPreferences, onSave}: UpdatePreferencesDialogProps) => {
    // State to track the local changes of preferences
    const [preferences, setPreferences] = useState(currentPreferences || []);
    const [newPreference, setNewPreference] = useState('');
    const [possibleDietPreferences, setPossibleDietPreferences] = useState<DietPreference[]>([]);
    const addPreference = () => {
        if (newPreference && !preferences.includes(newPreference)) {
            setPreferences([...preferences, newPreference]);
            setNewPreference('');
        }
    };

    const removePreference = (preference: string) => {
        setPreferences(preferences.filter((pref) => pref !== preference));
    };

    const handleSave = () => {
        onSave(preferences); // Pass the updated preferences back to the parent component
        onClose(); // Close the dialog after saving
    };

    // fetchDietPreferences
    useEffect(() => {
        fetchDietPreferences().then(setPossibleDietPreferences);
    }, []);

    return (
        <Dialog>
            <DialogTrigger>
                <Button variant="outline" onClick={() => setPreferences(currentPreferences || [])}><PencilIcon className="h-5 w-5 mr-2"/> Edit</Button>
            </DialogTrigger>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle className="">Update Food Preferences</DialogTitle>
                    <DialogDescription>
                        Add or remove food preferences
                    </DialogDescription>
                </DialogHeader>
                <div className="flex flex-wrap">
                    {preferences.map((preference, index) => (
                        <div key={index} className="flex items-center justify-between m-2">
                            <Button onClick={() => removePreference(preference)}
                                    className="hover:bg-red-500 hover:text-white">
                                {preference} <span className="ml-2 text-2xl">x</span>
                            </Button>
                        </div>
                    ))}
                </div>
                <div>
                    <div>
                        <label htmlFor="diet" className="block text-sm font-medium text-gray-700">Dietary
                            Preferences</label>
                        <div className="grid grid-cols-2 gap-2">
                            <div className={"col-start-1 col-span-3"}>
                                <DietPreferenceCombobox possibleDietPreferences={possibleDietPreferences}
                                                        setNewPreference={setNewPreference}/>
                            </div>
                            <Button onClick={addPreference} className="col-start-4">Add</Button>
                        </div>
                    </div>
                </div>
                <DialogFooter>
                    <DialogClose asChild>
                        {/*<Button onClick={onClose}>Cancel</Button>*/}
                        <Button variant='outline' onClick={handleSave}>Save</Button>
                    </DialogClose>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
};

export default UpdatePreferencesDialog;
