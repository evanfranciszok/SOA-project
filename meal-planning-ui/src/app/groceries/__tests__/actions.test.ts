// src/app/groceries/__tests__/actions.test.ts
import { fetchShoppingList } from '../actions';

describe('fetchShoppingList', () => {
    beforeEach(() => {
        global.fetch = jest.fn(() =>
            Promise.resolve({
                ok: true,
                json: () => Promise.resolve({ shoppingListPerStore: [] }),
            })
        ) as jest.Mock;
    });

    it('fetches data successfully', async () => {
        const userId = 'testUser123';
        const data = await fetchShoppingList(userId);
        expect(global.fetch).toHaveBeenCalledWith(
            `${process.env.SHOPPING_LIST_API_URL}/shoppingLists?userId=${userId}`
        );
        expect(data).toEqual({ shoppingListPerStore: [] });
    });

    it('throws an error when the response is not ok', async () => {
        global.fetch = jest.fn(() =>
            Promise.resolve({
                ok: false,
                statusText: 'Internal Server Error',
            })
        ) as jest.Mock;
        await expect(fetchShoppingList('testUser123')).rejects.toThrow('Failed to fetch shopping list for user testUser123');
    });
});
