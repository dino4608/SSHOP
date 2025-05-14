import { APP } from '@/app/layout';
import React from 'react';

export const AppFooter = () => {
    return (
        <div className="w-full bg-black text-white h-100 p-8 sm:p-12 lg:p-20">
            <div className="container h-full flex justify-center items-center mx-auto px-4 sm:px-20">
                <p>&copy; {new Date().getFullYear()} {APP.name}. All rights reserved.</p>
            </div>
        </div>
    );
};