'use client';

import React from "react";
import { Product } from "@/types/product-domain.type";
import ProductItem from "./product-grid/ProductItem";
import HomeContainer from "./HomeContainer";

const allProducts: Product[] = [
    { id: 1, name: "Short Nam", category: "Men's Clothing", price: 79000 },
    { id: 2, name: "Dây chuyền Helios", category: "Accessories", price: 152000 },
    { id: 3, name: "Giày thể thao", category: "Shoes", price: 320000 },
    { id: 4, name: "Dao nhà bếp", category: "Kitchen", price: 119000 },
    { id: 5, name: "Áo sơ mi", category: "Men's Clothing", price: 99000 },
    { id: 11, name: "Short Nam", category: "Men's Clothing", price: 79000 },
    { id: 12, name: "Dây chuyền Helios", category: "Accessories", price: 152000 },
    { id: 13, name: "Giày thể thao", category: "Shoes", price: 320000 },
    { id: 14, name: "Dao nhà bếp", category: "Kitchen", price: 119000 },
    { id: 15, name: "Áo sơ mi", category: "Men's Clothing", price: 99000 },
    { id: 21, name: "Short Nam", category: "Men's Clothing", price: 79000 },
    { id: 22, name: "Dây chuyền Helios", category: "Accessories", price: 152000 },
    { id: 23, name: "Giày thể thao", category: "Shoes", price: 320000 },
    { id: 24, name: "Dao nhà bếp", category: "Kitchen", price: 119000 },
    { id: 25, name: "Áo sơ mi", category: "Men's Clothing", price: 99000 },
];

type Props = {
    selectedCategory: string | null;
}

const ProductGridSection: React.FC<Props> = ({ selectedCategory }) => {
    const filteredProducts = !selectedCategory || selectedCategory === "All"
        ? allProducts
        : allProducts.filter((p) => p.category === selectedCategory);

    // px-2 sm:px-10 lg:px-20
    return (
        <HomeContainer>
            <div className="grid grid-cols-6 gap-4">
                {filteredProducts.map((product) => (
                    <ProductItem key={product.id} product={product} />
                ))}
            </div>
        </HomeContainer>
    );
};

export default ProductGridSection;