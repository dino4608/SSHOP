'use client';

import { app } from '@/app/utils';
import { ShoppingCart } from 'lucide-react';
import Link from 'next/link';
import React from 'react';
import LeftNav from './header/LeftNav';
import RightNav from './header/RightNav';
import SearchBar from './header/SearchBar';

const Header: React.FC = () => {
    // height = padding y + text + border = 16*2 + 32 + 0.8 = 6px (referenced)
    return (
        <header className='bg-white/80 w-full px-2 sm:px-6 lg:px-12 xl:px-20 py-2 sm:py-4 border-b border-gray-200 shadow-sm backdrop-blur-sm sticky top-0 z-50'>
            <div className='h-8 container mx-auto flex justify-between items-center text-back font-medium'>
                {/* Logo */}
                <Link
                    href={'/'}
                    className='text-[var(--dino-red-1)] text-xl sm:text-2xl font-bold tracking-tight'
                >
                    {app.name}
                </Link>

                <LeftNav />

                <SearchBar />

                <RightNav />

                {/* Cart */}
                <Link
                    href={'/cart'}
                    className='relative'
                >
                    <ShoppingCart />
                    <span className='
                        absolute -top-2 -right-4 w-6 h-4 rounded-full bg-[var(--dino-red-1)] text-white text-xs font-semibold
                        flex items-center justify-center'>
                        68
                    </span>
                </Link>
            </div>
        </header>
    );
};

export default Header;