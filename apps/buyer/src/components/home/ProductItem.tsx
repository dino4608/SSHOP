'use client';
import { RESOURCES } from "@/lib/constants";
import { getFileUrl } from "@/lib/files";
import { TProductItem } from "@/types/product.types";
import Image from "next/image";
import Link from "next/link";
import React from "react";

type ProductItemProps = {
    product: TProductItem;
}

export const ProductItem = ({ product }: ProductItemProps) => {
    return (
        <Link href={`/product/${product.id}`}>
            <div className="w-full overflow-hidden rounded-lg bg-white shadow">
                {/* Product image */}
                <div className="relative aspect-square">
                    <Image
                        src={getFileUrl(product.thumb, RESOURCES.PRODUCTS.BASE, product.id)}
                        alt="Product Image"
                        fill={true}
                        className="object-over"
                        loading="lazy"
                    />
                </div>

                <div className="p-2 space-y-1">
                    {/* Product title */}
                    <div className="text-sm text-gray-800 line-clamp-2 ">
                        {product.name}
                    </div>
                    {/* Product price */}
                    <div className="flex items-center space-x-2">
                        <span className="text-lg text-red-500 font-semibold ">
                            149.000₫
                        </span>
                        <span className="text-xs text-gray-400 line-through ">
                            {product.retailPrice}
                        </span>
                    </div>
                    {/* Free shipping */}
                    <div className="text-xs font-semibold space-x-2">
                        <span className="px-1 bg-teal-50 rounded-sm text-teal-500 border border-teal-100">
                            Free shipping
                        </span>
                        {product.meta.isCodEnabled && <span className="px-1 bg-white rounded-sm text-red-500 border border-red-500">
                            COD
                        </span>}
                    </div>
                    {/* Stars and sales */}
                    <div className="text-xs text-gray-400">
                        ⭐4.9 | 600 sold
                    </div>
                </div>
            </div>
        </Link>
    );
};