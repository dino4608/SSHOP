'use client';
import React from "react";

type THomeContainerProps = {
    children: React.ReactNode;
}

const HomeContainer = ({ children }: THomeContainerProps) => {
    return (
        <div className='w-full px-2 sm:px-10 lg:px-20'>
            <div className='container mx-auto'>
                {children}
            </div>
        </div>
    );
};

export default HomeContainer;