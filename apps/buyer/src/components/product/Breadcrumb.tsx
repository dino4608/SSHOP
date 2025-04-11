'use client';

import { ChevronRight, Home } from 'lucide-react';
import Link from 'next/link';
import React from 'react';

const Breadcrumb: React.FC = () => {

    return (
        <div className='bg-white border-b border-gray-200'>
            <div className="container mx-auto px-2 sm:px-8 lg:px-40">
                <div className='flex justify-start items-center text-sm text-gray-500 space-x-2 py-3 px-3'>
                    <Link href="/home" className="hover:text-blue-500 transition-colors flex items-center space-x-2">
                        <Home className='w-4 h-4' />
                        <span>Home</span>
                    </Link>

                    <ChevronRight className='w-4 h-4' />

                    <Link href="/category/1" className="hover:text-blue-500 transition-colors">
                        Category 1
                    </Link>

                    <ChevronRight className='w-4 h-4' />

                    <Link href="/category/1/1" className="hover:text-blue-500 transition-colors">
                        Category 1.1
                    </Link>

                    <ChevronRight className='w-4 h-4' />

                    <span className="text-gray-500 max-w-[400px] truncate">
                        Product name [Demo with a pro max plus mega super ultra extra long text]
                    </span>
                </div>
            </div>
        </div>
    );
}

export default Breadcrumb;