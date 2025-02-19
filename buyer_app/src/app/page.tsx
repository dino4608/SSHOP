'use server';

import { getCurrentSession } from "@/actions/auth";
import SalesCampaignBanner from "@/components/layout/SalesCampaignBanner";
import ProductGrid from "@/components/product/ProductGrid";
import { getAllProducts } from "@/server/product/api";

const Home = async () => {
  const { user } = await getCurrentSession();

  const products = await getAllProducts();

  return (
    <div>
      <SalesCampaignBanner />

      <div className="container mx-auto py-8">
        <ProductGrid products={products} />
      </div>
    </div>

  );
}

export default Home;
