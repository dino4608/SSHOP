'use server';

import Breadcrumb from '@/components/product/Breadcrumb';
import ProductSidebar from '@/components/product/ProductSidebar';
import ProductImages from '@/components/product/ProductImages';
import React from 'react';

type PageProps = {
    params: Promise<{
        id: string;
    }>;
};

const ProductDetailPage: React.FC<PageProps> = async ({ params }) => {
    const { id } = await params;
    console.log('Product ID:', id);


    return (
        <>
            <Breadcrumb />

            {/* Product Info */}
            <div className="container mx-auto px-2 sm:px-8 lg:px-40">
                <div className="py-4 flex gap-15 relative">
                    {/* Left Column */}
                    <div className="w-5/9 space-y-6">
                        <ProductImages />

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
                    <ProductSidebar />
                </div>
            </div>

        </>
    );
};

export default ProductDetailPage;