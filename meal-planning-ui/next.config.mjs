import {default as withPWA} from 'next-pwa';

/** @type {import('next').NextConfig} */
const nextConfig = {
    output: "standalone",
    reactStrictMode: true,
    compiler: {
        removeConsole: process.env.NODE_ENV !== "development", // Remove console.log in production
    },
    images: {
        remotePatterns: [
            {
                protocol: "https",
                hostname: "avatars.githubusercontent.com",
            }
        ]
    }
}

// Configuration object tells the next-pwa plugin.
const pwaConfig = {
    dest: "public", // Destination directory for the PWA files
    // disable: process.env.NODE_ENV === "development", // Disable PWA in development mode
    register: true, // Register the PWA service worker
    skipWaiting: true, // Skip waiting for service worker activation
};

export default withPWA(pwaConfig)(nextConfig);