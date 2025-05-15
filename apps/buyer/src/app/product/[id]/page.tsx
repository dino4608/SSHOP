'use client';
import Breadcrumb from '@/components/product/Breadcrumb';
import ProductActions from '@/components/product/ProductActions';
import ProductDeals from '@/components/product/ProductDeals';
import ProductDelivery from '@/components/product/ProductDelivery';
import ProductDescription from '@/components/product/ProductDescription';
import ProductImages from '@/components/product/ProductImages';
import ProductShopInfo from '@/components/product/ProductShopInfo';
import { api } from '@/lib/api';
import { clientFetch } from '@/lib/fetch/fetch.client';
import { TProduct } from '@/types/product.types';
import { useParams } from 'next/navigation';
import React, { useEffect, useState } from 'react';


const ProductDetailPage = () => {
    const { id } = useParams<{ id: string }>();
    const [product, setProduct] = useState<TProduct>({} as TProduct);
    const [selectedImage, setSelectedImage] = useState<string | null>(null);

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const apiRes = await clientFetch(api.products.getById(id));
                setProduct(apiRes.data);
            } catch (error) {
                console.error('>>> Error fetching products:', error);
            }
        };

        fetchProduct();
    }, [])

    // px-2 sm:px-8 lg:px-35
    // REFERENCED: height = padding top = 16px
    return (
        <>
            <Breadcrumb />

            {/* Product Info */}
            <div className="px-2 sm:px-10 lg:px-35">
                <div className="container mx-auto">
                    <div className="pt-4 pb-6 flex gap-10 relative">
                        {/* Left Column */}
                        <div className="w-8/15 space-y-6">
                            <ProductImages selectedImage={selectedImage} setSelectedImage={setSelectedImage} />

                            <ProductDelivery />

                            <ProductDeals />

                            <ProductShopInfo />

                            <ProductDescription />

                            <div className="h-72 rounded-lg bg-green-400 flex items-center justify-center text-white font-bold text-xl">Coupons from DealShop</div>
                            <div className="h-72 rounded-lg bg-amber-400 flex items-center justify-center text-white font-bold text-xl">Gift</div>
                            <div className="h-72 rounded-lg bg-purple-400 flex items-center justify-center text-white font-bold text-xl">Bundles</div>
                            <div className="h-72 rounded-lg bg-pink-400 flex items-center justify-center text-white font-bold text-xl">Reviews</div>
                        </div>

                        {/* Right Column */}
                        <div className="w-7/15">
                            <ProductActions onSelectVariant={setSelectedImage} {...product} />
                        </div>
                    </div>

                    {/* Recommendation */}
                    <div className="mb-6 h-200 rounded-lg bg-sky-400 flex items-center justify-center text-white font-bold text-xl">Recommended Products</div>
                </div>
            </div>
        </>
    );
};

export default ProductDetailPage;