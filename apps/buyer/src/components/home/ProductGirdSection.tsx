'use client';
import { api } from "@/lib/api";
import { clientFetch } from "@/lib/fetch/fetch.client";
import { TPagination } from "@/types/base.types";
import { TProductItem } from "@/types/product.types";
import { useEffect, useState } from "react";
import HomeContainer from "./HomeContainer";
import { ProductItem } from "./ProductItem";

type TProductGridSectionProps = {
    selectedCategory: string | null;
}

export const ProductGridSection = ({ selectedCategory }: TProductGridSectionProps) => {
    const [products, setProducts] = useState<TProductItem[]>([]);
    const [loading, setLoading] = useState(true);
    const [pagination, setPagination] = useState<TPagination>({} as TPagination);

    // const filteredProducts = !selectedCategory || selectedCategory === "All"
    //     ? allProducts
    //     : allProducts.filter((p) => p.category === selectedCategory);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const apiRes = await clientFetch(api.products.list());
                setProducts(apiRes.data.items);
                setPagination(apiRes.data.pagination);
            } catch (error) {
                console.error('>>> Error fetching products:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []); // [] nghĩa là chỉ gọi 1 lần khi component mount

    // px-2 sm:px-10 lg:px-20
    return (
        <HomeContainer>
            <div className="grid grid-cols-6 gap-4">
                {products.map((product) => (
                    <ProductItem key={product.id} product={product} />
                ))}
            </div>
        </HomeContainer>
    );
};