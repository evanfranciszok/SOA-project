import type {Metadata, Viewport} from "next";
import {Inter} from "next/font/google";
import "./globals.css";
import React from "react";
import Navbar from "@/components/Navbar";
import NextAuthProvider from "@/app/context/NextAuthProvider";

const inter = Inter({subsets: ["latin"]});

export const viewport: Viewport = {
    themeColor: "#ffffff",
    width: "device-width",
    initialScale: 1,
    minimumScale: 1,
    viewportFit: "cover",
};
export const metadata: Metadata = {
    title: "Happie",
    description: "Happie Meal Planner",
    generator: "Next.js",
    manifest: "/manifest.json",
    keywords: ["nextjs", "nextjs13", "next13", "pwa", "next-pwa"],
    authors: [
        {name: "Floris Vossebeld"},
        {
            name: "Floris Vossebeld",
            url: "https://www.vossebeld.dev",
        },
    ],
    icons: [
        {rel: "apple-touch-icon", url: "icons/apple-touch-icon.png"},
        {rel: "icon", url: "icons/mstile-150x150.png"},
    ],
};

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
        <body className={inter.className}>
        <NextAuthProvider>
            <div className="flex flex-col min-h-screen">
                <Navbar/>
                <main className="flex-grow container mx-auto p-5 overflow-auto">
                    {/*    Add children*/}
                    {children}
                </main>
            </div>
        </NextAuthProvider>
        </body>

        </html>
    );
}
