'use client';

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

const ProductImages: React.FC = () => {
    const [selectedImage, setSelectedImage] = useState(imageUrls[0]);

    return (
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
    );
};

export default ProductImages;