'use client';

import { app } from '@/app/utils';
import Link from 'next/link';
import React from 'react';

const Header: React.FC = () => {
    return (
        <header className='w-full sticky top-0 z-50 backdrop-blur-sm bg-white/80 py-2 sm:py-4 border-b-gray-100 shadow-sm'>
            <div className='container mx-auto px-2 sm:px-8 lg:px-20 flex justify-between items-center'>
                <Link href={'/deal'}>
                    <span className='text-xl sm:text-2xl font-bold tracking-tight'>{app.name}</span>
                </Link>
                <div>Category</div>
                <div>Search</div>
                <div>Account</div>
                <div>Cart</div>
            </div>
        </header>
    );
};

export default Header;