import { render, waitFor } from '@testing-library/react';
import Groceries from '../page';
import { useSession } from 'next-auth/react';
import { fetchShoppingList } from '../actions';
import React from 'react';

jest.mock('next-auth/react');
jest.mock('../actions');

describe('Groceries Page', () => {
    it('renders correctly when user is authenticated and has no shopping list items', async () => {
        const mockedUseSession = useSession as jest.Mock;
        const mockedFetchShoppingList = fetchShoppingList as jest.Mock;

        mockedUseSession.mockReturnValue({
            data: { user: { id: 'abc123' } },
            status: 'authenticated'
        });
        mockedFetchShoppingList.mockResolvedValue({ shoppingListPerStore: [] });

        render(<Groceries />);
        await waitFor(() => expect(mockedFetchShoppingList).toHaveBeenCalledWith('abc123'));
        // Assertions can be added here
    });
});
