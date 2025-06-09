'use client';

import React, { useState } from "react";
import HomeContainer from "./HomeContainer";
import { ChevronLeft, ChevronRight } from "lucide-react";
import Image from "next/image";
import useCountdown from "@/functions/useCountDown";

const flashSaleProducts = [
    {
        id: 1,
        image: "/images/flash-sale/blanket.webp",
        title: "Chiếu Điều Hòa Sợi Mây Tự Nhiên HOMIES Bedding & Décor Viền Vải - Chất Liệu Thiên Nhiên Cao Cấp Mát Lạnh Mùa Hè",
        soldPercent: 85,
        price: "₫28.900",
    },
    {
        id: 2,
        image: "/images/flash-sale/blissberry.webp",
        title: "Serum BHA cấp ẩm kiềm dầu Blissberry Pureskin Daily Gentle Calming 15ml",
        soldPercent: 48,
        price: "₫234.000",
    },
    {
        id: 3,
        image: "/images/flash-sale/buds.webp",
        title: "Tai nghe Bluetooth True Wireless Samsung Galaxy Buds 3 Pro",
        soldPercent: 91,
        price: "₫3.990.000",
    },
    {
        id: 4,
        image: "/images/flash-sale/case.webp",
        title: "Ốp Điện Thoại tpu Mềm Trong Suốt Chống Sốc Dạng Ví Đựng Thẻ Cho iphone 15 14 13 12 11 pro max 8 7 plus",
        soldPercent: 16,
        price: "₫11.644",
    },
    {
        id: 5,
        image: "/images/flash-sale/clean.webp",
        title: "Thùng 30 gói giấy ăn gấu trúc rút 3 màu cao cấp Top Gia 4 lớp dày dặn, mềm mịn",
        soldPercent: 46,
        price: "₫75.000",
    },
    {
        id: 6,
        image: "/images/flash-sale/coolmate.webp",
        title: "Quần dài nam Kaki Excool co giãn đàn hồi Coolmate",
        soldPercent: 89,
        price: "₫428.000",
    },
    {
        id: 7,
        image: "/images/flash-sale/food.webp",
        title: "Hộp đựng thức ăn cho bé Lock&Lock baby food container 280ml bằng thủy tinh borosilicate và nhựa tritan LLG542S3IVY",
        soldPercent: 72,
        price: "₫369.000",
    },
    {
        id: 8,
        image: "/images/flash-sale/simple.webp",
        title: "Sữa Rửa Mặt Simple lành tính và hiệu quả cho mọi loại da 150ml",
        soldPercent: 38,
        price: "₫109.000",
    },
    {
        id: 11,
        image: "/images/flash-sale/blanket.webp",
        title: "Chiếu Điều Hòa Sợi Mây Tự Nhiên HOMIES Bedding & Décor Viền Vải - Chất Liệu Thiên Nhiên Cao Cấp Mát Lạnh Mùa Hè",
        soldPercent: 85,
        price: "₫28.900",
    },
    {
        id: 12,
        image: "/images/flash-sale/blissberry.webp",
        title: "Serum BHA cấp ẩm kiềm dầu Blissberry Pureskin Daily Gentle Calming 15ml",
        soldPercent: 48,
        price: "₫234.000",
    },
    {
        id: 13,
        image: "/images/flash-sale/buds.webp",
        title: "Tai nghe Bluetooth True Wireless Samsung Galaxy Buds 3 Pro",
        soldPercent: 91,
        price: "₫3.990.000",
    },
    {
        id: 14,
        image: "/images/flash-sale/case.webp",
        title: "Ốp Điện Thoại tpu Mềm Trong Suốt Chống Sốc Dạng Ví Đựng Thẻ Cho iphone 15 14 13 12 11 pro max 8 7 plus",
        soldPercent: 16,
        price: "₫11.644",
    },
    {
        id: 15,
        image: "/images/flash-sale/clean.webp",
        title: "Thùng 30 gói giấy ăn gấu trúc rút 3 màu cao cấp Top Gia 4 lớp dày dặn, mềm mịn",
        soldPercent: 46,
        price: "₫75.000",
    },
    {
        id: 16,
        image: "/images/flash-sale/coolmate.webp",
        title: "Quần dài nam Kaki Excool co giãn đàn hồi Coolmate",
        soldPercent: 89,
        price: "₫428.000",
    },
    {
        id: 17,
        image: "/images/flash-sale/food.webp",
        title: "Hộp đựng thức ăn cho bé Lock&Lock baby food container 280ml bằng thủy tinh borosilicate và nhựa tritan LLG542S3IVY",
        soldPercent: 72,
        price: "₫369.000",
    },
    {
        id: 18,
        image: "/images/flash-sale/simple.webp",
        title: "Sữa Rửa Mặt Simple lành tính và hiệu quả cho mọi loại da 150ml",
        soldPercent: 38,
        price: "₫109.000",
    },
];

const ITEMS_PER_PAGE = 6;

const FlashSaleSection = () => {
    const [startIndex, setStartIndex] = useState(0);
    const maxIndex = flashSaleProducts.length - ITEMS_PER_PAGE;

    const handlePrev = () => {
        if (startIndex === 0) return;
        setStartIndex(prev => Math.max(prev - ITEMS_PER_PAGE, 0));
    };

    const handleNext = () => {
        if (startIndex >= maxIndex) return;
        setStartIndex(prev => Math.min(prev + ITEMS_PER_PAGE, maxIndex));
    };

    const visibleProducts = flashSaleProducts.slice(startIndex, startIndex + ITEMS_PER_PAGE);

    const { hours, minutes, seconds } = useCountdown(60 * (60 * 12 + 28) + 45); // 6h 18m 45s

    return (
        <HomeContainer>
            <div className="bg-gradient-to-b from-rose-100 to-white p-4 space-y-4 rounded-lg border border-gray-200 relative">
                {/* header */}
                <div className="flex justify-between items-center">
                    {/* title */}
                    <div className="flex items-center gap-2">
                        <span className="text-2xl font-extrabold text-black">
                            ⚡Flash Sale
                        </span>
                        <span className="bg-[var(--dino-red-1)] text-white text-base font-bold p-2 leading-none rounded-md">
                            70% Off
                        </span>
                    </div>

                    {/* count down time */}
                    <div className="flex items-center gap-1 text-base font-bold">
                        <span className="bg-black text-white h-8 w-10 rounded-md flex justify-center items-center">{hours}</span>
                        <span>:</span>
                        <span className="bg-black text-white h-8 w-10  rounded-md flex justify-center items-center">{minutes}</span>
                        <span>:</span>
                        <span className="bg-black text-white h-8 w-10 rounded-md flex justify-center items-center">{seconds}</span>
                    </div>
                </div>

                {/* products list // TODO: make carousel slower */}
                <div className="grid grid-cols-6 gap-4 transition-all ease-in-out">
                    {visibleProducts.map((item) => (
                        <div key={item.id} className="overflow-hidden">
                            <div className="aspect-square bg-white rounded-lg overflow-hidden relative">
                                {/* product image */}
                                <Image
                                    src={item.image}
                                    alt={item.title}
                                    fill
                                    className="object-cover rounded-lg" />

                                {/* sales percent */}
                                <div className="
                                    absolute bottom-0 left-0 right-0 overflow-hidden
                                    h-6 bg-black/40 text-xs font-semibold rounded-b-lg
                                    flex items-center justify-start"
                                >
                                    <div
                                        className="
                                            h-full bg-gradient-to-r from-orange-400 via-[var(--dino-red-1)] to-[var(--dino-red-1)]
                                            flex items-center justify-start text-white"
                                        style={{ width: `${item.soldPercent}%` }}
                                    >
                                        <p className="px-4">
                                            {item.soldPercent}%
                                        </p>
                                    </div>
                                </div>
                            </div>

                            {/* price */}
                            <p className="px-2 pt-4 font-bold text-xl text-black">{item.price}</p>
                        </div>
                    ))}
                </div>

                {/* carousel controls */}
                {startIndex > 0 && (
                    <button
                        onClick={handlePrev}
                        className="
                            absolute -left-4 top-1/2 -translate-y-1/2 z-10
                            bg-white size-8 border rounded-full p-1 shadow hover:bg-rose-100
                            flex items-center justify-center"
                    >
                        <ChevronLeft className="w-4 text-[var(--dino-red-1)]" />
                    </button>
                )}
                {startIndex < maxIndex && (
                    <button
                        onClick={handleNext}
                        className="
                            absolute -right-4 top-1/2 -translate-y-1/2 z-10
                            bg-white size-8 border rounded-full p-1 shadow hover:bg-rose-100
                             flex items-center justify-center"
                    >
                        <ChevronRight className="w-4 text-[var(--dino-red-1)]" />
                    </button>
                )}

            </div>
        </HomeContainer>
    );
};

export default FlashSaleSection;
