'use server';

import React from 'react';

type ProductDetailProps = {
    params: Promise<{
        id: string;
    }>;
};

// This is a demo product detail page
// It uses a dependent-scrolled two-column layout
// A left column for product details
// A right column for actions.
const ProductDetailPage = async ({ params }: ProductDetailProps) => {
    const { id } = await params;
    console.log(">>> ProductDetailPage rendered with id:", id);

    return (
        <div className="flex h-screen overflow-hidden">
            <div className='container mx-auto px-2 sm:px-10 lg:px-20 flex'>
                {/* Left Column */}
                <div className="w-3/5 overflow-y-scroll bg-gray-100 p-4 space-y-6">
                    <div className="h-72 rounded-lg bg-red-400 flex items-center justify-center text-white font-bold text-xl">Product Image</div>
                    <div className="h-72 rounded-lg bg-yellow-400 flex items-center justify-center text-white font-bold text-xl">Estimated Delivery</div>
                    <div className="h-72 rounded-lg bg-green-400 flex items-center justify-center text-white font-bold text-xl">Entertainment</div>
                    <div className="h-72 rounded-lg bg-blue-400 flex items-center justify-center text-white font-bold text-xl">Deals</div>
                    <div className="h-72 rounded-lg bg-purple-400 flex items-center justify-center text-white font-bold text-xl">Bundles</div>
                    <div className="h-72 rounded-lg bg-pink-400 flex items-center justify-center text-white font-bold text-xl">Reviews</div>
                    <div className="h-72 rounded-lg bg-amber-400 flex items-center justify-center text-white font-bold text-xl">Shop Info</div>
                    <div className="h-72 rounded-lg bg-lime-400 flex items-center justify-center text-white font-bold text-xl">Description</div>
                    <div className="h-72 rounded-lg bg-sky-400 flex items-center justify-center text-white font-bold text-xl">Recommended Products</div>
                    <div className="h-720">Test its height</div>
                </div>

                {/* Right Column */}
                <div className="w-2/5 overflow-y-scroll p-6 bg-white border-l border-gray-300">
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

                    <div className="h-720">Test its height</div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetailPage;