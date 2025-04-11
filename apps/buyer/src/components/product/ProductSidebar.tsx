'use server';

import { TicketCheck } from 'lucide-react';
import React from 'react';

const ProductSidebar: React.FC = () => {
    return (
        <div className="w-4/9 flex flex-col transition-all duration-300 sticky top-20 self-start">
            {/* Product price */}
            <div className='flex flex-col gap-2 mb-3'>
                {/* Discounted price */}
                <div className='flex items-center text-4xl text-red-500 font-semibold tracking-tighter gap-0.5'>
                    <span className='text-xl'>₫</span>400.000
                    <span className='text-xl'>-</span>
                    <span className='text-xl'>₫</span>600.000
                </div>

                <div className='flex gap-1'>
                    {/* Discount figures */}
                    <div className='inline-flex justify-center items-center text-sm text-red-500 bg-red-100 rounded-sm px-1.5 py-0.5 animate-pulse'>
                        <TicketCheck className='w-4 h-4 mx-0.5' />-50% | -₫600.000
                    </div>

                    {/* Base pice */}
                    <div className='flex items-center text-base text-gray-400 line-through'>
                        ₫800.000-₫1.200.000
                    </div>
                </div>
            </div>

            {/* Product name & meta */}
            <div className='flex flex-col gap-2 mb-3 border-b border-gray-200 pb-3'>
                <h1 className="text-xl font-medium">Awesome Wireless Earbuds, BLUETOOTH 6.0, Fingerprint Touch Headphones with Noise Reduction Microphone</h1>
                <div className="text-base text-gray-500">⭐ 4.8 | 🔥 2.3K sold | 🏠 by GadgetStore</div>
            </div>

            {/* Deals */}
            <div className="flex items-center gap-2 mb-3 border-b border-gray-200 pb-3 text-base">
                Deals:
                <span className="inline-flex px-2 bg-red-100 rounded-sm text-red-500 font-semibold">
                    12% off, max ₫200K
                </span>
                <span className="inline-flex px-2 bg-red-100 rounded-sm text-red-500 font-semibold">
                    15% off, max ₫300K
                </span>
            </div>

            {/* Shipping */}
            <div className="flex items-center gap-2 mb-3 border-b border-gray-200 pb-3 text-base">
                Shipping:
                <span className="inline-flex px-2 bg-cyan-100 rounded-sm text-cyan-500 font-semibold">
                    Free shipping
                </span>
                <span className="inline-flex text-gray-400 line-through">
                    ₫17.000
                </span>
                Get by 14 -17 April

            </div>

            {/* Guarantee */}



            {/* Interaction buttons */}
            <div className="space-y-3">
                <button className="w-full py-2 bg-red-200 text-red-800 rounded-md font-semibold">Buy Now</button>
                <button className="w-full py-2 bg-green-100 text-green-800 rounded-md font-semibold">Add to Cart</button>
                <div className="flex gap-3">
                    <button className="flex-1 py-2 bg-blue-100 text-blue-800 rounded-md font-semibold">Shop</button>
                    <button className="flex-1 py-2 bg-purple-100 text-purple-800 rounded-md font-semibold">Chat</button>
                </div>
            </div>
        </div>
    );
}

export default ProductSidebar;