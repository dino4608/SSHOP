'use client';

import Image from 'next/image';
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

// todo: cover img to Image
const ProductImages: React.FC = () => {
    const [selectedImage, setSelectedImage] = useState(imageUrls[0]);

    return (
        <div className="flex gap-4">
            {/* Thumbnails */}
            <div className="flex flex-col gap-2 overflow-y-auto scrollbar-hidden max-h-[100%]">
                <div className="aspect-square h-full flex flex-col gap-2 over">
                    {imageUrls.map((url) => (
                        <Image
                            key={url}
                            src={url}
                            alt={`Thumbnail ${url}`}
                            onMouseEnter={() => setSelectedImage(url)}
                            width={64}
                            height={64}
                            className={`w-16 h-16 object-cover rounded cursor-pointer border-2 ${selectedImage === url ? 'border-blue-500' : 'border-transparent'}`}
                        />
                    ))}
                </div>
            </div>

            {/* Main Image */}
            <div className="flex-1 w-full aspect-square bg-gray-100 rounded-lg flex items-center justify-center relative">
                <Image
                    src={selectedImage}
                    alt="Selected Product"
                    fill
                    className="object-contain rounded"
                    sizes="(min-width: 1024px) 500px, 100vw"
                />
            </div>
        </div>
    );
};

export default ProductImages;