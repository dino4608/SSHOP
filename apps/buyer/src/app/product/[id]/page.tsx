import { ProductDetail } from '@/components/product/ProductDetail';
import { api } from '@/lib/api';
import { serverFetch } from '@/lib/fetch/fetch.server';
import { asyncIsAuthenticated } from '@/lib/server/auth';
import { TAddress } from '@/types/address.types';

type ProductDetailPageProps = {
    params: Promise<{ id: string; }>;
};

const ProductDetailPage = async ({ params }: ProductDetailPageProps) => {
    const { id } = await params;
    const isAuthenticated = await asyncIsAuthenticated();

    const apiRes = await serverFetch(api.products.getById(id));
    const product = apiRes.data;

    async function asyncDefaultAddress(isAuthenticated: boolean) {
        if (!isAuthenticated) return null;
        const apiRes = await serverFetch<TAddress>(api.addresses.getDefault());
        return apiRes.data;
    }
    const defaultAddress = await asyncDefaultAddress(isAuthenticated);

    return (
        <ProductDetail product={product} defaultAddress={defaultAddress} />
    );
};

export default ProductDetailPage;