// components/PageTitle.tsx

import React from 'react';

interface PageTitleProps {
    title: string;
    description: string;
    button?: React.ReactNode; // Add button prop
}

const PageTitle = ({ title, description, button }: PageTitleProps) => (
    <div className="flex justify-between items-center"> {/* Add flexbox */}
        <div>
            <h1 className="text-3xl font-semibold">{title}</h1>
            <p className="text-gray-500">{description}</p>
        </div>
        {button && <div>{button}</div>} {/* Display button if provided */}
    </div>
);

export default PageTitle;