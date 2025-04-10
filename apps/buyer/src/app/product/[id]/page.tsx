'use server';

import ProductDetail from '@/components/product/ProductDetail';
import React from 'react';

type PageProps = {
    params: Promise<{
        id: string;
    }>;
};

const ProductDetailPage: React.FC<PageProps> = async ({ params }) => {
    const { id } = await params;

    return (
        <ProductDetail productId={id} />
    );
};

export default ProductDetailPage;