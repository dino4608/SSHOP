'use client';

import { Product } from "@/server/product/api";
import ProductItem from "./ProductItem";

type ProductGridProps = {
    products: Product[];
};

const ProductGrid = ({ products }: ProductGridProps) => {
    return (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4">
            {products.map((product) => (
                <ProductItem
                    key={product.id}
                    product={product}
                />
            ))};
        </div>
    );
};

export default ProductGrid;