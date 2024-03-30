// components/PageTitle.tsx

import React from 'react';

interface PageTitleProps {
    title: string;
    description: string;
}

const PageTitle = ({ title, description }: PageTitleProps) => (
    <div>
        <h1 className="text-3xl font-semibold">{title}</h1>
        <p className="text-gray-500">{description}</p>
    </div>
);

export default PageTitle;