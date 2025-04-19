'use client';

import FlashSaleSection from "@/components/home/FlashSaleSection";
import HeroSection from "@/components/home/HeroSection";
import ProductGrid from "@/components/home/ProductGird";

const HomePage = () => {
  return (
    <div className="py-6 space-y-6">
      <HeroSection />

      <FlashSaleSection />

      <ProductGrid />
    </div>
  );
}

export default HomePage;
