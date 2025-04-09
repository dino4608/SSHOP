'use server';

import ProductClientSide from '@/components/product/ProductClientSide';
import React from 'react';

type PageProps = {
    params: Promise<{
        id: string;
    }>;
};

const ProductDetailPage: React.FC<PageProps> = async ({ params }) => {
    const { id } = await params;

    return (
        <ProductClientSide productId={id} />
    );
};

export default ProductDetailPage;