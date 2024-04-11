import {NextAuthOptions} from 'next-auth';
import GithubProvider from 'next-auth/providers/github';
import {MongoDBAdapter} from "@auth/mongodb-adapter";
import clientPromise from "@/lib/db";
import {Adapter} from "next-auth/adapters";

export const authOptions: NextAuthOptions = {
    adapter: <Adapter>MongoDBAdapter(clientPromise),
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
        session({session, token, user}) {
            if (session?.user) {
                session.user.id = user.id as string
            }
            return session // The return type will match the one returned in `useSession()`
        },
    }
};