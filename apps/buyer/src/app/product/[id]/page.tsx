import { ProductDetail } from '@/components/product/ProductDetail';
import { api } from '@/lib/api';
import { serverFetch } from '@/lib/fetch/fetch.server';

type ProductDetailPageProps = {
    params: Promise<{ id: string; }>;
};

const ProductDetailPage = async ({ params }: ProductDetailPageProps) => {
    const { id } = await params;
    const apiRes = await serverFetch(api.products.getById(id));

    // TODO: handle serverFetch error

    return (
        <ProductDetail product={apiRes.data} />
    );
};

export default ProductDetailPage;