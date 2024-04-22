import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import Loading from '../loading';
import React from 'react';


describe('Loading Component', () => {
    it('renders correctly', () => {
        render(<Loading />);
        expect(screen.getByText('Your weekly meal schedule')).toBeInTheDocument();
    });
});
