import { ProductDetail } from '@/components/product/ProductDetail';
import { api } from '@/lib/api';
import { serverFetch } from '@/lib/fetch/fetch.server';
import { getIsAuthenticated } from '@/hooks/getIsAuthenticated';

type ProductDetailPageProps = {
    params: Promise<{ id: string; }>;
};

const ProductDetailPage = async ({ params }: ProductDetailPageProps) => {
    const { id } = await params;
    const isAuthenticated = await getIsAuthenticated();

    const apiRes = await serverFetch(api.products.getById(id));
    const product = apiRes.data;

    return (
        <ProductDetail product={product} />
    );
};

export default ProductDetailPage;