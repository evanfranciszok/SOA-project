// app/components/Container.tsx
import React from 'react';

interface ContainerProps {
    children: React.ReactNode;
}

const Container = ({ children }: ContainerProps) => (
    <div className="max-w-4xl mx-auto px-4 py-4 overflow-auto">{children}</div>
);

export default Container;