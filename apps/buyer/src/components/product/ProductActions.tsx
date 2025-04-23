'use client';

import { MessageCircle, Store, TicketCheck } from 'lucide-react';
import Image from 'next/image';
import React, { useState } from 'react';

const colors = [
    { code: "GZR9915", image: "/images/jeans1.jpg", disabled: false },
    { code: "GZR9916", image: "/images/jeans2.jpg", disabled: false },
    { code: "GZR9921", image: "/images/jeans3.jpg", disabled: false },
    { code: "GZR9923", image: "/images/jeans4.jpg", disabled: false },
    { code: "GZR9919", image: "/images/jeans5.jpg", disabled: true },
    { code: "GZR9918", image: "/images/jeans6.jpg", disabled: false },
];

const sizes = [
    { label: "S (45–55kg)", value: "S" },
    { label: "M (55–64kg)", value: "M" },
    { label: "L (64–74kg)", value: "L" },
    { label: "XL (74–84kg)", value: "XL" },
];

type Props = {
    onVariantSelect: (img: string) => void;
};

// select the first variant: ${selectedColor === code ? 'border-[var(--dino-red-1)] text-black' : 'border-gray-200'}
// hover variants: 'hover:border-black'
const ProductActions = ({ onVariantSelect }: Props) => {
    const [selectedColor, setSelectedColor] = useState<string | null>(null);
    const [selectedSize, setSelectedSize] = useState<string | null>(null);
    const [quantity, setQuantity] = useState<number>(1);

    const handleQuantityChange = (delta: number) => {
        setQuantity(prev => Math.max(1, prev + delta));
    };

    // Max of Actions area is 100vh - header - breadcrumb - padding of ProductClientSide (referencing)
    return (
        <div className='max-h-[calc(100vh-65px-33px-16px)] flex flex-col transition-all duration-300 sticky top-20 self-start divide-y divide-gray-200'>
            {/* Short info of product */}
            <div className='overflow-y-auto scrollbar-hidden space-y-4 pb-4'>
                {/* Product name // todo: (55) should be blue */}
                <div className='flex flex-col gap-1'>
                    <h1 className="text-lg font-medium">Awesome Wireless Earbuds, BLUETOOTH 6.0, Fingerprint Touch Headphones with Noise Reduction Microphone</h1>
                    <div className="text-sm text-gray-500">⭐ 4.8 (55) | 🔥 2.3K sold | 🏠 by GadgetStore</div>
                </div>

                {/* Product price */}
                <div className='p-1 bg-gray-50 flex flex-col gap-1'>
                    {/* Discounted price */}
                    <div className='flex items-center text-3xl text-[var(--dino-red-1)] font-semibold tracking-tighter gap-0.5'>
                        <span className='text-xl'>₫</span>
                        400.000
                        <span className='text-xl'>- ₫</span>
                        600.000
                    </div>

                    <div className='flex gap-1'>
                        {/* Base price */}
                        <div className='flex items-center text-sm text-gray-400 line-through'>
                            ₫800.000-₫1.200.000
                        </div>

                        {/* Discount figures */}
                        <div className='inline-flex justify-center items-center text-xs text-red-500 bg-red-100 rounded-sm px-1.5 py-0.5 animate-pulse'>
                            <TicketCheck className='w-4 h-4 mx-0.5' />-50% | -₫600.000
                        </div>
                    </div>
                </div>

                {/* Variations and quantity */}
                <div className="space-y-4 text-sm">
                    {/* Color */}
                    <div>
                        <div className="font-medium mb-2">Colors</div>
                        <div className='flex flex-wrap gap-2'>
                            {colors.map(({ code, image, disabled }) => (
                                <button
                                    key={code}
                                    disabled={disabled}
                                    onClick={() => {
                                        if (!disabled) {
                                            setSelectedColor(code);
                                            onVariantSelect(image); // Send its parent the image state
                                        }
                                    }}
                                    className={`
                                        flex items-center justify-center border-2 rounded-sm
                                        ${selectedColor === code
                                            ? 'border-[var(--dino-red-1)] bg-[var(--dino-red-1)] text-white'
                                            : 'border-gray-300'}
                                        ${disabled
                                            ? 'opacity-40 cursor-not-allowed'
                                            : ''}
                                    `}
                                >
                                    {/* todo: change to Image/Next */}
                                    <div className="w-8 aspect-square relative">
                                        <Image
                                            src={image}
                                            alt='A variant image'
                                            fill
                                            className="object-cover rounded-sm"
                                        />
                                    </div>
                                    <div className='py-1 px-2'>
                                        {code}
                                    </div>
                                </button>
                            ))}
                        </div>
                    </div>

                    {/* Size */}
                    <div>
                        <div className="font-medium mb-2">Sizes</div>
                        <div className="flex flex-wrap gap-2">
                            {sizes.map(({ label, value }) => (
                                <button
                                    key={value}
                                    onClick={() => setSelectedSize(value)}
                                    className={`
                                        border-2 rounded-sm
                                        ${selectedSize === value
                                            ? 'border-[var(--dino-red-1)] bg-[var(--dino-red-1)] text-white'
                                            : 'border-gray-300'}
                                    `}
                                >
                                    <div className='py-1 px-2'>
                                        {label}
                                    </div>
                                </button>
                            ))}
                        </div>
                    </div>

                    {/* Quantity */}
                    <div className="flex items-center gap-4">
                        <div className="font-medium">Quantity</div>
                        <div className="flex items-center border rounded-md overflow-hidden w-fit">
                            <button
                                onClick={() => handleQuantityChange(-1)}
                                className="px-3 py-1 text-xl">-</button>
                            <div className="px-4 py-1 border-l border-r">{quantity}</div>
                            <button
                                onClick={() => handleQuantityChange(1)}
                                className="px-3 py-1 text-xl">+</button>
                        </div>
                    </div>
                </div>
            </div>

            {/* Interaction buttons */}
            <div className="flex items-center gap-4 sticky bottom-0 bg-white px-2 py-4 z-10">
                <div className="flex gap-4">
                    {/* Visit shop button */}
                    <button className="flex flex-col items-center text-sm text-black">
                        <Store className="w-5 h-5" />
                        <span className='text-sm'>Shop</span>
                    </button>

                    {/* Chat with shop button */}
                    <button className="relative flex flex-col items-center text-sm text-black">
                        <MessageCircle className="w-5 h-5" />
                        <span>Chat</span>
                        <span className="absolute -top-2 -right-1 bg-[var(--dino-red-1)] text-white text-xs font-semibold w-5 h-5 rounded-full flex items-center justify-center">
                            1
                        </span>
                    </button>
                </div>

                {/* Main action buttons */}
                <div className="flex-1 flex gap-4">
                    <button className="flex-1 py-1.5 bg-gray-100 text-black rounded-md font-medium text-base">
                        Add to cart
                    </button>
                    <button className="flex-1 py-1 bg-[var(--dino-red-1)] text-white rounded-md font-medium text-base flex flex-col items-center justify-start">
                        <span>Buy now</span>
                        <span className="text-xs font-normal">Free shipping</span>
                    </button>
                </div>
            </div>
        </div >
    );
}

export default ProductActions;