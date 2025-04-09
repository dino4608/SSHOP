'use client';

import React from 'react';
import Cart from './header/Cart';
import LeftNavigation from './header/LeftNavigation';
import Logo from './header/Logo';
import RightNavigation from './header/RightNavigation';
import SearchBar from './header/SearchBar';

const Header: React.FC = () => {
    // height = padding y + text + border = 16*2 + 32 + 0.8 = 65px (referenced)
    return (
        <header className='
            bg-white w-full px-2 sm:px-6 lg:px-12 xl:px-20 py-2 sm:py-4
            border-b border-gray-200 shadow-sm backdrop-blur-sm
            sticky top-0 z-50'
        >
            <div className='h-8 container mx-auto flex justify-between items-center text-back font-medium'>
                <Logo />

                <LeftNavigation />

                <SearchBar />

                <RightNavigation />

                <Cart />
            </div>
        </header>
    );
};

export default Header;