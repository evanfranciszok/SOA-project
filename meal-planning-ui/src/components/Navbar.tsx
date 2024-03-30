'use client'
import clsx from 'clsx';
import {usePathname} from "next/navigation";
import Image from 'next/image';
import React from 'react';
import {ChevronDownIcon, ShoppingBagIcon, HomeIcon, UserIcon, CalendarDaysIcon} from '@heroicons/react/24/outline'; // Ensure these icons are installed via @heroicons/react
import Logo from '../../public/icons/android-chrome-512x512.png'
import AvatarImage from '../../public/avatar.webp';
import Link from "next/link";

const nav_items = [
    {
        title: 'Home',
        icon: <HomeIcon/>,
        show_on_mobile: true,
        show_on_desktop: true,
        route: '/',
    },
    {
        title: 'Schedule',
        icon: <CalendarDaysIcon/>,
        show_on_mobile: true,
        show_on_desktop: true,
        route: '/schedule',
    },
    {
        title: 'Groceries',
        icon: <ShoppingBagIcon/>,
        show_on_mobile: true,
        show_on_desktop: true,
        route: '/groceries',
    },
    {
        title: 'Profile',
        icon: <UserIcon/>,
        show_on_mobile: true,
        show_on_desktop: false,
        route: '/profile',
    },
];
const Navbar = () => {
    return (
        <>
            <nav className="bg-white border-b border-gray-200 px-4 py-2 md:flex justify-between items-center hidden">
                <div className="flex items-center pl-2">
                    <Image src={Logo} alt="Happie Logo" width={30} height={30}/>
                    <span className="font-semibold text-lg ml-2">Happie</span>
                </div>
                <div className="flex items-center">
                    {nav_items.map((item, index) => (
                        item.show_on_desktop &&
                        <NavItem key={index} title={item.title} icon={item.icon} route={item.route}/>
                    ))}
                    <AvatarDropdown/>
                </div>
            </nav>
            {/*    Mobile navigation */}
            <div
                className="fixed bottom-0 left-0 z-50 w-full h-16 bg-white border-t border-gray-200 dark:bg-gray-700 dark:border-gray-600 md:hidden"
                style={{marginBottom: 'env(safe-area-inset-bottom)'}}>
                <div className="grid h-full max-w-lg grid-cols-4 mx-auto font-medium">
                    {nav_items.map((item, index) => (
                        item.show_on_mobile &&
                        <MobileNavItem key={index} title={item.title} icon={item.icon} route={item.route}/>
                    ))}
                </div>
            </div>

        </>
    );
};

// Props for the NavItem component
interface NavItemProps {
    icon: React.ReactNode;
    title: string;
    route: string;
}

const NavItem = ({icon, title, route}: NavItemProps) => (
    // Use the usePathname hook to get the current route
    <Link href={route}>
        <div
            className={clsx("pl-5 flex items-center cursor-pointer hover:text-blue-600 dark:hover:text-blue-500", usePathname() === route && "text-blue-600 dark:text-blue-500")}>
            <div className="w-6 h-6">{icon}</div>
            <span className="text-sm font-medium ml-1.5">{title}</span>
        </div>
    </Link>
    // <div className="pl-5 flex items-center cursor-pointer hover:text-blue-600 dark:hover:text-blue-500">
    //     <div className="w-6 h-6">{icon}</div>
    //     <span className="text-sm font-medium ml-1.5">{title}</span>
    // </div>
);

const MobileNavItem = ({icon, title, route}: NavItemProps) => (
    // Use the usePathname hook to get the current route
    <Link type="button" href={route}
            className={clsx("inline-flex flex-col items-center justify-center px-5 hover:bg-gray-50 dark:hover:bg-gray-800 group", {"text-indigo-600 dark:text-blue-500 font-bold ": usePathname() == route})}>
        <div
            className="w-5 h-5 mb-1 dark:text-gray-400 group-hover:text-blue-600 dark:group-hover:text-blue-500"
            aria-hidden="true">
            {icon}
        </div>
        <span
            className="text-sm group-hover:text-blue-600 dark:group-hover:text-blue-500">{title}</span>
    </Link>
);

const AvatarDropdown = () => (
    <div className="relative flex items-center ml-8">
        <div className="w-9 h-9 bg-gray-200 rounded-full">
            {/* Crop to a circle */}
            <Image src={AvatarImage} alt="User Avatar" className="rounded-full object-cover w-full h-full"/>
        </div>
        <ChevronDownIcon className="w-6 h-6 ml-2"/>
    </div>
);

export default Navbar;
