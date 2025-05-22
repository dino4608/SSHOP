'use client';
import { TProductBuyBox } from '@/types/product.types';
import { TSku } from '@/types/sku.types';
import { MessageCircle, Store, TicketCheck } from 'lucide-react';
import { useState } from 'react';
import { ProductSelector } from './ProductSelector';

type TProductActionsProps = {
    onSelectPhoto: (photo: string) => void;
    product: TProductBuyBox;
};

// select the first variant: ${selectedColor === code ? 'border-[var(--dino-red-1)] text-black' : 'border-gray-200'}
// hover variants: 'hover:border-black'
export const ProductBuyBox = ({ onSelectPhoto, product }: TProductActionsProps) => {
    const [selectedSku, setSelectedSku] = useState<TSku | null>(null);

    // Max of Actions area is 100vh - header - breadcrumb - padding of ProductClientSide (referencing)
    return (
        <div className='max-h-[calc(100vh-65px-33px-16px)] flex flex-col transition-all duration-300 sticky top-20 self-start divide-y divide-gray-200'>
            {/* Summary */}
            <div className='overflow-y-auto scrollbar-hidden space-y-4 pb-4'>
                {/* Product name // TODO: (55) should be blue */}
                <div className='flex flex-col gap-1'>
                    <h1 className="text-lg font-medium">{product.name}</h1>
                    <div className="text-sm text-gray-500">⭐ 4.8 (55) | 🔥 2.3K sold | 🏠 by {product.shop?.name}</div>
                </div>

                {/* Product price */}
                {selectedSku ? (
                    <div className='p-1 bg-gray-50 flex flex-col gap-1'>
                        {/* Discounted price */}
                        <div className='flex items-center text-3xl text-[var(--dino-red-1)] font-semibold tracking-tighter gap-0.5'>
                            <span className='text-xl'>₫</span>
                            400.000
                            <span className='text-xl'>- ₫</span>
                            600.000
                        </div>

                        {/* Retail price + Discount figures */}
                        <div className='flex gap-1'>

                            <div className='flex items-center text-sm text-gray-400 line-through'>
                                {`₫${selectedSku.retailPrice}`}
                            </div>

                            <div className='inline-flex justify-center items-center text-xs text-red-500 bg-red-100 rounded-sm px-1.5 py-0.5 animate-pulse'>
                                <TicketCheck className='w-4 h-4 mx-0.5' />-50% | -₫600.000
                            </div>
                        </div>
                    </div>
                ) : (
                    <div className='p-1 bg-gray-50 flex flex-col gap-1'>
                        {/* Discounted price */}
                        <div className='flex items-center text-3xl text-[var(--dino-red-1)] font-semibold tracking-tighter gap-0.5'>
                            <span className='text-xl'>₫</span>
                            400.000
                            <span className='text-xl'>- ₫</span>
                            600.000
                        </div>

                        {/* Retail price + Discount figures */}
                        <div className='flex gap-1'>

                            <div className='flex items-center text-sm text-gray-400 line-through'>
                                {`₫${product.retailPrice}-₫${product.retailPrice}`}
                            </div>

                            <div className='inline-flex justify-center items-center text-xs text-red-500 bg-red-100 rounded-sm px-1.5 py-0.5 animate-pulse'>
                                <TicketCheck className='w-4 h-4 mx-0.5' />-50% | -₫600.000
                            </div>
                        </div>
                    </div>
                )}

                {/* Variations and quantity */}
                <ProductSelector
                    onChangeSelectedSku={setSelectedSku}
                    onSelectPhoto={onSelectPhoto}
                    product={{ id: product.id, skus: product.skus, tierVariations: product.tierVariations }} />

            </div>

            {/* Interaction buttons */}
            <div className="flex items-center gap-4 sticky bottom-0 bg-white px-2 py-4 z-10">
                {/* Visit + Chat with shop buttons */}
                <div className="flex gap-4">
                    <button className="flex flex-col items-center text-sm text-black">
                        <Store className="w-5 h-5" />
                        <span className='text-sm'>Shop</span>
                    </button>

                    <button className="relative flex flex-col items-center text-sm text-black">
                        <MessageCircle className="w-5 h-5" />
                        <span>Chat</span>
                        <span className="absolute -top-2 -right-1 bg-[var(--dino-red-1)] text-white text-xs font-semibold w-5 h-5 rounded-full flex items-center justify-center">
                            1
                        </span>
                    </button>
                </div>

                {/* Cart + Buy buttons */}
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