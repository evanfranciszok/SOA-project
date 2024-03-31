// app/components/Container.tsx
import React from 'react';

interface ContainerProps {
    children: React.ReactNode;
}

const Container = ({ children }: ContainerProps) => (
    <div className="max-w-4xl mx-auto py-0 md:py-4 overflow-auto mt-11 md:mt-10">{children}</div>
);

export default Container;