'use client';

import ProductGrid from "@/components/home/ProductGird";

const HomePage = () => {
  return (
    <div>
      <div className="w-full h-20 flex justify-center items-center bg-while">Utilities bar</div>
      <div className="w-full h-20 flex justify-center items-center bg-gradient-to-r from-red-600 via-orange-400 to-red-600">Carousel images</div>
      <div className="w-full h-20 flex justify-center items-center" style={{ backgroundColor: 'rgb(254, 44, 85)' }}>Flash sale bar</div>
      <div className="w-full h-20 flex justify-center items-center" style={{ backgroundColor: 'rgb(0, 153, 149)' }}>Category bar</div>
      <ProductGrid />
    </div>
  );
}

export default HomePage;
