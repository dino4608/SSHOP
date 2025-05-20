'use client';
import { RESOURCES } from '@/lib/constants';
import { getFileUrl } from '@/lib/files';
import { TProductMedia } from '@/types/product.types';
import Image from 'next/image';
import { useState } from 'react';

type TProductMediaProps = {
    selectedImage: string | null;
    setSelectedImage: (img: string) => void;
    product: TProductMedia;
};

export const ProductMedia = ({ selectedImage, setSelectedImage, product }: TProductMediaProps) => {
    const images = [
        ...(product.thumb ? [product.thumb] : []),
        ...(product.video ? [product.video] : []),
        ...(product.photos ?? []),
    ];

    //
    // Thêm trạng thái
    const [showSizeGuide, setShowSizeGuide] = useState(false);

    // Tổng số ảnh chính
    const photoImages = [
        ...(product.thumb ? [product.thumb] : []),
        ...(product.photos ?? [])
    ];

    const currentIndex = photoImages.findIndex((img) => img === (selectedImage || product.thumb));
    const currentDisplay = `${currentIndex + 1}/${photoImages.length}`;
    //

    const displayImage = showSizeGuide
        ? product.sizeGuidePhoto!
        : selectedImage || images[0];


    return (
        <div className="flex gap-4">
            {/* Side photos */}
            <div className="flex flex-col gap-2 max-h-full overflow-y-auto scrollbar-hidden">
                <div className="h-full aspect-square flex flex-col gap-2">
                    {images.map((image) => (
                        <Image
                            key={image}
                            src={getFileUrl(image, RESOURCES.PRODUCTS.BASE, product.id)}
                            alt={`Photo ${image}`}
                            onMouseEnter={() => {
                                setSelectedImage(image);
                            }}
                            width={64}
                            height={64}
                            className={`w-16 h-16 object-cover rounded cursor-pointer border-2 ${selectedImage === image ? 'border-blue-500' : 'border-transparent'}`}
                        />
                    ))}
                </div>
            </div>

            {/* Main photo */}
            <div className="relative flex-1 w-full aspect-square bg-gray-100 rounded-lg flex items-center justify-center">
                <Image
                    src={getFileUrl(displayImage, RESOURCES.PRODUCTS.BASE, product.id)}
                    alt={`Thumbnail ${displayImage}`}
                    fill
                    className="object-contain rounded"
                    sizes="(min-width: 1024px) 500px, 100vw"
                    unoptimized
                    priority
                />

                {/* Bottom-right area */}
                <div className="absolute bottom-4 right-4 bg-gray-200 rounded-full flex text-xs overflow-hidden">
                    {/* Left: x/y */}
                    <button
                        className={`px-3 py-1 transition-colors ${!showSizeGuide ? 'bg-gray-700 text-white' : 'text-gray-500'
                            }`}
                        onClick={() => {
                            setShowSizeGuide(false);
                            setSelectedImage(photoImages[0]); // hoặc ảnh đang chọn trước đó
                        }}
                    >
                        {currentDisplay}
                    </button>

                    {/* Right: Size Guide */}
                    {product.sizeGuidePhoto && (
                        <button
                            className={`px-3 py-1 transition-colors ${showSizeGuide ? 'bg-gray-700 text-white' : 'text-gray-500'
                                }`}
                            onClick={() => {
                                setShowSizeGuide(true);
                                setSelectedImage(product.sizeGuidePhoto);
                            }}
                        >
                            Size
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
};
