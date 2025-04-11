'use server';

import React from 'react';

const ProductSidebar: React.FC = () => {
    return (
        <div className="w-4/9 space-y-4 transition-all duration-300 sticky top-20 self-start">
            <h1 className="text-2xl font-bold mb-2">Awesome Wireless Earbuds</h1>
            <div className="text-gray-500 mb-4">⭐ 4.8 | 2.3K Sold | by GadgetStore</div>

            <div className="mb-6">
                <div className="text-2xl text-red-600 font-bold">$19.99</div>
                <div className="line-through text-gray-400">$39.99</div>
                <div className="text-sm text-green-600">50% OFF | You save $20.00</div>
            </div>

            <div className="space-y-3">
                <button className="w-full py-2 bg-blue-100 text-blue-800 rounded-md font-semibold">Visit Shop</button>
                <button className="w-full py-2 bg-purple-100 text-purple-800 rounded-md font-semibold">Chat</button>
                <button className="w-full py-2 bg-green-100 text-green-800 rounded-md font-semibold">Add to Cart</button>
                <button className="w-full py-2 bg-red-200 text-red-800 rounded-md font-semibold">Buy Now</button>
            </div>
        </div>
    );
}

export default ProductSidebar;