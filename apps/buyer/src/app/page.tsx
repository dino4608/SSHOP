'use client';

import HeroSection from "@/components/home/HeroSection";
import ProductGrid from "@/components/home/ProductGird";

const HomePage = () => {
  return (
    <div className="py-6 space-y-6">
      <HeroSection />
      <div className="w-full h-20 flex justify-center items-center" style={{ backgroundColor: 'rgb(254, 44, 85)' }}>Flash sale bar</div>
      <ProductGrid />
    </div>
  );
}

export default HomePage;
