import {NextAuthOptions} from 'next-auth';
import GithubProvider from 'next-auth/providers/github';

export const authOptions: NextAuthOptions = {
    // Secret for Next-auth, without this JWT encryption/decryption won't work
    secret: process.env.NEXTAUTH_SECRET,

    // Configure one or more authentication providers
    providers: [
        GithubProvider({
            clientId: process.env.GITHUB_APP_CLIENT_ID as string,
            clientSecret: process.env.GITHUB_APP_CLIENT_SECRET as string,
        }),
    ],
    callbacks: {
        async redirect({url, baseUrl}) {
            // Use baseUrl as the base URL for the callback URL
            // Print the base URL to the console
            console.log(baseUrl);
            return baseUrl;
        }
    }
};