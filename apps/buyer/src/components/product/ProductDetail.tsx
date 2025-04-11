'use client';

import { ChevronRight, Home } from 'lucide-react';
import Link from 'next/link';
import React, { useState } from 'react';

const imageUrls = [
    '/square.jpg',
    '/laptop.jpg',
    '/ring.jpg',
    '/dog.jpg',
    '/watch.jpg',
    '/ipad.jpg',
    '/earphones.jpg',
    '/iphone.jpg',
    '/headphones.jpg',
    '/pocket3.jpg',
];

type ProductDetailProps = {
    productId: string;
};

const ProductDetail: React.FC<ProductDetailProps> = ({ productId }) => {
    console.log(`ProductDetail rendered with productId: ${productId}`);

    const [selectedImage, setSelectedImage] = useState(imageUrls[0]);

    return (
        <div className="bg-white">

            {/* Category Inheritance Section */}
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

            {/* Product Detail Section */}
            <div className="container mx-auto px-2 sm:px-8 lg:px-40">
                <div className="py-4 flex gap-15 relative">
                    {/* Left Column */}
                    <div className="w-5/9 space-y-6">
                        {/* Product Image */}
                        <div className="flex gap-4">
                            {/* Thumbnails */}
                            <div className="flex flex-col gap-2 overflow-y-auto scrollbar-hidden max-h-[100%]">
                                <div className="aspect-square h-full flex flex-col gap-2">
                                    {imageUrls.map((url, idx) => (
                                        <img
                                            key={idx}
                                            src={url}
                                            alt={`Thumbnail ${idx + 1}`}
                                            onMouseEnter={() => setSelectedImage(url)}
                                            className={`w-16 h-16 object-cover rounded cursor-pointer border-2 ${selectedImage === url ? 'border-blue-500' : 'border-transparent'
                                                }`}
                                        />
                                    ))}
                                </div>
                            </div>

                            {/* Main Image */}
                            <div className="flex-1 w-full aspect-square bg-gray-100 rounded-lg flex items-center justify-center">
                                <img src={selectedImage} alt="Selected Product" className="max-h-full max-w-full object-contain rounded" />
                            </div>
                        </div>

                        <div className="h-72 rounded-lg bg-yellow-400 flex items-center justify-center text-white font-bold text-xl">Estimated Delivery</div>
                        <div className="h-72 rounded-lg bg-green-400 flex items-center justify-center text-white font-bold text-xl">Entertainment</div>
                        <div className="h-72 rounded-lg bg-blue-400 flex items-center justify-center text-white font-bold text-xl">Deals</div>
                        <div className="h-72 rounded-lg bg-purple-400 flex items-center justify-center text-white font-bold text-xl">Bundles</div>
                        <div className="h-72 rounded-lg bg-pink-400 flex items-center justify-center text-white font-bold text-xl">Reviews</div>
                        <div className="h-72 rounded-lg bg-amber-400 flex items-center justify-center text-white font-bold text-xl">Shop Info</div>
                        <div className="h-72 rounded-lg bg-lime-400 flex items-center justify-center text-white font-bold text-xl">Description</div>
                        <div className="h-72 rounded-lg bg-sky-400 flex items-center justify-center text-white font-bold text-xl">Recommended Products</div>
                    </div>

                    {/* Right Column */}
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
                </div>
            </div>
        </div>
    );
};

export default ProductDetail;