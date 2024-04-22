import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import Home from '../page';
import {useSession} from 'next-auth/react';
import React from 'react';
import NextAuthProvider from "@/app/context/NextAuthProvider";

jest.mock('next-auth/react');

describe('Home Page', () => {
    it('displays loading when status is loading', () => {
        (useSession as jest.Mock).mockReturnValue({status: 'loading'});
        render(<Home/>);
        expect(screen.getByText('Loading...')).toBeInTheDocument();
    });

    it('prompts for login when no user session is present', () => {
        (useSession as jest.Mock).mockReturnValue({data: null, status: 'unauthenticated'});
        render(<Home/>);
        expect(screen.getByText('You need to log in to view this page')).toBeInTheDocument();
        expect(screen.getByRole('button', {name: /sign in/i})).toBeInTheDocument();
    });

    it('displays MealSchedule when user is logged in', () => {
        (useSession as jest.Mock).mockReturnValue({
            data: {
                user: {
                    name: 'John Doe',
                    id: 'test-user-id' // Add a user ID here
                }
            },
            status: 'authenticated'
        });
        render(<NextAuthProvider><Home/></NextAuthProvider>);
        expect(screen.getByTestId('meal-schedule')).toBeInTheDocument();  // Assuming MealSchedule has a testId 'meal-schedule'
    });
});
