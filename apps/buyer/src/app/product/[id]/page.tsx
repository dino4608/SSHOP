import { ProductDetail } from '@/components/product/ProductDetail';
import { api } from '@/lib/api';
import { serverFetch } from '@/lib/fetch/fetch.server';

type ProductDetailPageProps = {
    params: Promise<{ id: string; }>;
};

const ProductDetailPage = async ({ params }: ProductDetailPageProps) => {
    const { id } = await params;

    const product = (await serverFetch(api.products.getById(id))).data;

    return (
        <ProductDetail product={product} />
    );
};

export default ProductDetailPage;