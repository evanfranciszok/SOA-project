'use client'
import clsx from 'clsx';
import {usePathname} from "next/navigation";
import {signOut, useSession} from "next-auth/react";
import Image from 'next/image';
import React from 'react';
import {
    ChevronDownIcon, ShoppingBagIcon as ShoppingBagIconActive,
    HomeIcon as HomeIconActive, UserIcon as UserIconActive,
    ArchiveBoxIcon as ArchiveBoxIconActive
} from '@heroicons/react/24/solid'; // Ensure these icons are installed via @heroicons/react
import {ShoppingBagIcon, HomeIcon, UserIcon, ArchiveBoxIcon} from '@heroicons/react/24/outline'; // Ensure these icons are installed via @heroicons/react
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'; // Ensure these components are installed via @headlessui/react
import Logo from '../../public/icons/android-chrome-512x512.png'
import AvatarImage from '../../public/avatar.webp';
import Link from "next/link";
import {Carrot, User} from "lucide-react";

const nav_items = [
    {
        title: 'Home',
        icon: <HomeIcon/>,
        active_icon: <HomeIconActive/>,
        show_on_mobile: true,
        show_on_desktop: true,
        route: '/',
    },
    {
        title: 'Inventory',
        icon: <ArchiveBoxIcon/>,
        active_icon: <ArchiveBoxIconActive/>,
        show_on_mobile: true,
        show_on_desktop: true,
        route: '/inventory',
    },
    {
        title: 'Groceries',
        icon: <ShoppingBagIcon/>,
        active_icon: <ShoppingBagIconActive/>,
        show_on_mobile: true,
        show_on_desktop: true,
        route: '/groceries',
    },
    {
        title: 'Profile',
        icon: <UserIcon/>,
        active_icon: <UserIconActive/>,
        show_on_mobile: true,
        show_on_desktop: false,
        route: '/profile',
    },
];
const Navbar = () => {
    return (
        <>
            <nav
                className="bg-white border-b border-gray-200 px-4 pt-0 pb-4 md:py-2 md:flex justify-between items-center fixed w-full z-50"
                style={{top: 'env(safe-area-inset-top)'}}>
                <div className="flex items-center md:pl-2">
                    <div className="flex mx-auto">
                        <Image src={Logo} alt="Happie Logo" width={30} height={30}/>
                        <span className="md:text-xl text-2xl ml-2 text-primary font-bold">Happie</span>
                    </div>
                </div>
                <div className="hidden md:flex items-center">
                    {nav_items.map((item, index) => (
                        item.show_on_desktop &&
                        <NavItem key={index} title={item.title} icon={item.icon} route={item.route}
                                 active_icon={item.active_icon}/>
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
                        <MobileNavItem key={index} title={item.title} icon={item.icon} route={item.route}
                                       active_icon={item.active_icon}/>
                    ))}
                </div>
            </div>

        </>
    );
};

// Props for the NavItem component
interface NavItemProps {
    icon: React.ReactNode;
    active_icon: React.ReactNode;
    title: string;
    route: string;
}

function NavItem({icon, active_icon, title, route}: NavItemProps) {
    // Use the usePathname hook to get the current route
    const isActive = usePathname() === route;
    return (
        <Link href={route}>
            <div
                className={clsx("pl-5 flex items-center cursor-pointer hover:text-green-950 dark:hover:text-blue-500", isActive && "text-primary dark:text-primary")}>
                <div className="w-6 h-6">{isActive ? active_icon : icon}</div>
                <span className="text-sm font-medium ml-1.5">{title}</span>
            </div>
        </Link>
    )
        // <div className="pl-5 flex items-center cursor-pointer hover:text-blue-600 dark:hover:text-blue-500">
        //     <div className="w-6 h-6">{icon}</div>
        //     <span className="text-sm font-medium ml-1.5">{title}</span>
        // </div>
        ;
}

function MobileNavItem({icon, active_icon, title, route}: NavItemProps) {

    const isActive = usePathname() === route;
    return (
        // Use the usePathname hook to get the current route
        <Link type="button" href={route}
              className={clsx("inline-flex flex-col items-center justify-center px-5 group", {"text-primary dark:text-primary font-bold ": usePathname() == route})}>
            <div
                className="w-5 h-5 mb-1"
                aria-hidden="true">
                {isActive ? active_icon : icon}
            </div>
            <span
                className="text-sm">{title}</span>
        </Link>
    );
}

function AvatarDropdown() {
    const {data: session, status} = useSession();
    const user = session?.user;
    if (status === 'loading') return null;
    if (!user) return null;


    return (
        <div className="relative flex items-center ml-8">
            <DropdownMenu>
                <DropdownMenuTrigger asChild>
                    <div className="flex items-center cursor-pointer">
                        <div className="font-medium mr-3">
                            {user.name}
                        </div>
                        <div className="w-9 h-9 bg-gray-200 rounded-full">
                            {/* Crop to a circle */}
                            <Image src={AvatarImage} alt="User Avatar"
                                   className="rounded-full object-cover w-full h-full"/>
                        </div>
                        <ChevronDownIcon className="w-6 h-6 ml-2"/>
                    </div>
                </DropdownMenuTrigger>
                <DropdownMenuContent className="w-56">
                    <DropdownMenuLabel>My Account</DropdownMenuLabel>
                    <DropdownMenuSeparator/>
                    <Link href={'/profile?activeTab=general'}>
                        <DropdownMenuItem>
                            <User className="mr-2 h-4 w-4"/>
                            <span>Profile</span>
                        </DropdownMenuItem>
                    </Link>
                    <Link href={'/profile?activeTab=foodpreferences'}>
                        <DropdownMenuItem>
                            <Carrot className="mr-2 h-4 w-4"/>
                            <span>Food Preferences</span>
                        </DropdownMenuItem>
                    </Link>
                    <DropdownMenuSeparator/>
                    <DropdownMenuItem onClick={() => signOut()}>
                        <span>Log Out</span>
                    </DropdownMenuItem>
                </DropdownMenuContent>
            </DropdownMenu>
        </div>
    )
}

export default Navbar;
