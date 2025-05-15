'use client';

import CategoryListSection from "@/components/home/CategoryListSection";
import FlashSaleSection from "@/components/home/FlashSaleSection";
import HeroImageSection from "@/components/home/HeroImageSection";
import ProductGridSection from "@/components/home/ProductGirdSection";
import { useState } from "react";


const HomePage = () => {
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

  return (
    <div className="py-10 space-y-10">
      <HeroImageSection />

      <FlashSaleSection />

      <CategoryListSection
        selectedCategory={selectedCategory}
        setSelectCategory={setSelectedCategory}
      />

      <ProductGridSection
        selectedCategory={selectedCategory}
      />
    </div>
  );
}

export default HomePage;
