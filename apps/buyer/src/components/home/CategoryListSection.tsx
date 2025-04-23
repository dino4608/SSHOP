'use client';

import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from "@/components/ui/carousel";
import HomeContainer from "./HomeContainer";

// Giả lập dữ liệu category
const categories = ["All", "Men's Clothing", "Accessories", "Shoes", "Kitchen", "Woman's Clothing", "Electronics",
    "Baby", "Cosmetics", "Toys", "Sports", "Health", "Books", "Pet Supplies", "Tools", "Home", "Garden",
    "Grocery", "Jewelry", "Watches", "Musical Instruments", "Office Supplies",];
// Giả lập dữ liệu sản phẩm

const chunkArray = (arr: string[], size: number): string[][] => {
    const result: string[][] = [];
    for (let i = 0; i < arr.length; i += size) {
        result.push(arr.slice(i, i + size));
    }
    return result;
};

type Props = {
    selectedCategory: string | null;
    setSelectCategory: (cat: string) => void;
}

const CategoryListSection = ({ selectedCategory, setSelectCategory }: Props) => {
    const groupedCategories = chunkArray(categories, 5);

    return (
        <>
            {/* Danh mục sản phẩm với Carousel */}
            <div className="
                bg-white mt-15 border-b border-gray-200 shadow-sm backdrop-blur-sm
                sticky top-[65px] z-10"
            >
                <HomeContainer>
                    <Carousel className="w-full relative">
                        <CarouselContent className="mx-2">
                            {groupedCategories.map((group, idx) => (
                                <CarouselItem key={idx} className="basis-auto px-2">
                                    <div className="flex gap-4 justify-between">
                                        {group.map((cat) => (
                                            <button
                                                key={cat}
                                                onClick={() => setSelectCategory(cat)}
                                                className={`p-3 whitespace-nowrap transition-colors ${selectedCategory === cat
                                                    ? "border-b-3 border-[var(--dino-red-1)] text-[var(--dino-red-1)] font-semibold"
                                                    : "text-gray-600"
                                                    }`}
                                            >
                                                {cat}
                                            </button>
                                        ))}
                                    </div>
                                </CarouselItem>
                            ))}
                        </CarouselContent>
                        <CarouselPrevious className="absolute -left-4" />
                        <CarouselNext className="absolute -right-4" />
                    </Carousel>
                </HomeContainer>
            </div>
        </>
    );
};

export default CategoryListSection;