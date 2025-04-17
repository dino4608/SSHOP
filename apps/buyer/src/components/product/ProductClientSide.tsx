'use client';

import { useState } from "react";
import Breadcrumb from "./Breadcrumb";
import ProductActions from "./ProductActions";
import ProductDeals from "./ProductDeals";
import ProductDelivery from "./ProductDelivery";
import ProductDescription from "./ProductDescription";
import ProductImages from "./ProductImages";
import ProductShopInfo from "./ProductShopInfo";

type ProductClientSideProps = {
    productId: string;
};

const ProductClientSide: React.FC<ProductClientSideProps> = ({ productId }) => {
    console.log(productId);

    const [selectedImage, setSelectedImage] = useState<string | null>(null);

    // px-2 sm:px-8 lg:px-35
    // height = padding top = 16px (referenced)
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
                            <ProductActions onVariantSelect={setSelectedImage} />
                        </div>
                    </div>

                    {/* Recommendation */}
                    <div className="mb-6 h-200 rounded-lg bg-sky-400 flex items-center justify-center text-white font-bold text-xl">Recommended Products</div>
                </div>
            </div>
        </>
    );
};

export default ProductClientSide
    ;