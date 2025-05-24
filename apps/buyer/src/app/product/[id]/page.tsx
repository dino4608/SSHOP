import { ProductDetail } from '@/components/product/ProductDetail';
import { getIsAuthenticated } from '@/hooks/getIsAuthenticated';
import { api } from '@/lib/api';
import { serverFetch } from '@/lib/fetch/fetch.server';

type ProductDetailPageProps = {
    params: Promise<{ id: string; }>;
};

const ProductDetailPage = async ({ params }: ProductDetailPageProps) => {
    const { id } = await params;
    const isAuthenticated = await getIsAuthenticated();

    const product = (await serverFetch(api.products.getById(id))).data;
    const discountedProduct = isAuthenticated
        ? (await serverFetch(api.discountedProduct.getByProductId(id))).data
        : null;

    return (
        <ProductDetail product={product} discountedProduct={discountedProduct} />
    );
};

export default ProductDetailPage;