// components/ProfileGeneralPreferences.tsx
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

import React from "react";

export default function ProfileGeneralPreferences() {
    return (
        <div>
            <p className="text-gray-500">Your general preferences</p>
            <div className="mt-4">
                <div className="flex items-center justify-between">
                    <div className="flex items-center">
                        <div className="">
                            <h2 className="text-lg font-semibold">Theme</h2>
                        </div>
                    </div>
                    <Select>
                        <SelectTrigger className="w-1/2">
                            <SelectValue placeholder="Select theme">Auto</SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectGroup>
                                <SelectLabel>Theme</SelectLabel>
                                <SelectItem value="auto">Use system setting</SelectItem>
                                <SelectItem value="light">Light</SelectItem>
                                <SelectItem value="dark">Dark</SelectItem>
                            </SelectGroup>
                        </SelectContent>
                    </Select>
                </div>
                <div className="flex items-center justify-between mt-4">
                    <div className="flex items-center">
                        <div className="">
                            <h2 className="text-lg font-semibold">Language</h2>
                        </div>
                    </div>
                    <Select>
                        <SelectTrigger className="w-1/2">
                            <SelectValue placeholder="Select language">English</SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectGroup>
                                <SelectLabel>Language</SelectLabel>
                                <SelectItem value="en">English</SelectItem>
                                <SelectItem value="es">Spanish</SelectItem>
                                <SelectItem value="fr">French</SelectItem>
                            </SelectGroup>
                        </SelectContent>
                    </Select>
                </div>
            </div>
        </div>
    );
}