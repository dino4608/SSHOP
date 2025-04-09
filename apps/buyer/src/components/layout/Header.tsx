'use client';

import { app } from '@/app/utils';
import Link from 'next/link';
import React from 'react';

const Header: React.FC = () => {
    // height = padding y + text + border = 16*2 + 32 + 0.8 = 6px (referenced)
    // /bg-white/80 text-black // bg-black text-white // bg-[var(--dino-red-1)] text-white //  bg-[var(--dino-red-1)] text-black
    // px-2 sm:px-10 lg:px-20
    return (
        <header className='bg-white text-[var(--dino-red-3)] w-full px-2 sm:px-10 lg:px-20 py-2 sm:py-4 border-b border-gray-200 shadow-sm backdrop-blur-sm sticky top-0 z-50'>
            <div className='h-8 container mx-auto flex justify-between items-center'>
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