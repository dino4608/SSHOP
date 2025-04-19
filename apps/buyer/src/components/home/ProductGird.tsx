'use client';

import ProductItem from "./ProductItem";

const ProductGrid = () => {
    // px-2 sm:px-10 lg:px-20
    return (
        <div className="w-full bg-white px-2 sm:px-10 lg:px-20">
            <div className="container mx-auto grid grid-cols-6 gap-4">
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
                <ProductItem />
            </div>
        </div>

    );
};

export default ProductGrid;