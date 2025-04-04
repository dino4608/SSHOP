'use server';

import { getCurrentSession } from "@/actions/auth";
import { getWheelOfFortuneConfiguration } from "@/actions/wheel-of-fortune-actions";
import SalesCampaignBanner from "@/components/layout/SalesCampaignBanner";
import WheelOfFortune from "@/components/layout/WheelOfFortune";
import ProductGrid from "@/components/product/ProductGrid";
import { getAllProducts } from "@/server/product/api";

const Home = async () => {
  const { user } = await getCurrentSession();

  const products = await getAllProducts();

  const { randomProducts, winningIndex } = await getWheelOfFortuneConfiguration();

  return (
    <div>
      <SalesCampaignBanner />

      <WheelOfFortune
        products={randomProducts}
        winningIndex={winningIndex}
      />

      <div className="container mx-auto py-8">
        <ProductGrid products={products} />
      </div>
    </div>

  );
}

export default Home;
