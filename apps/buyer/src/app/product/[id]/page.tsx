'use server';

import ProductClientSide from '@/components/product/ProductClientSide';
import React from 'react';

type PageProps = {
    params: Promise<{
        id: string;
    }>;
};

const ProductDetailPage = async ({ params }: PageProps) => {
    const { id } = await params;

    return (
        <ProductClientSide productId={id} />
    );
};

export default ProductDetailPage;