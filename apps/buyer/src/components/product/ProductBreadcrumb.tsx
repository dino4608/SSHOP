'use client';

import { TProductBreadcrumb } from '@/types/product.types';
import { ChevronRight, Home } from 'lucide-react';
import Link from 'next/link';
import React from 'react';

type TProductBreadcrumbProps = {
    product: TProductBreadcrumb;
};

const ProductBreadcrumb = ({ product }: TProductBreadcrumbProps) => {
    // REFERENCED: height = padding y + text + border = 8*2 + 16 + 0.8 = 32.8px
    // px-2 sm:px-10 lg:px-35
    return (
        <div className='px-2 sm:px-10 lg:px-35 py-2 bg-white border-b border-gray-200'>
            <div className="">
                <div className='h-4 container mx-auto flex justify-start items-center text-xs text-gray-500 space-x-2'>
                    <Link href="/" className="hover:text-blue-500 transition-colors flex items-center space-x-2">
                        <Home className='w-3 h-3' />
                        <span>Home</span>
                    </Link>

                    <ChevronRight className='w-3 h-3' />

                    <Link href={`/category/${product.category.id}`} className="hover:text-blue-500 transition-colors">
                        {product.category.name}
                    </Link>

                    {/* <ChevronRight className='w-3 h-3' />

                    <Link href="/category/1/1" className="hover:text-blue-500 transition-colors">
                        Category 1.1
                    </Link> */}

                    <ChevronRight className='w-3 h-3' />

                    <span className="text-gray-500 max-w-[400px] truncate">
                        {product.name}
                    </span>
                </div>
            </div>
        </div>
    );
}

export default ProductBreadcrumb;