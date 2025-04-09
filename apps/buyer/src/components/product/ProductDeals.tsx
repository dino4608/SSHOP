'use client';

import { Ticket } from "lucide-react";
import React, { useRef, useState } from 'react';

const ProductDeals: React.FC = () => {
    const scrollRef = useRef<HTMLDivElement>(null);
    const [isDragging, setIsDragging] = useState(false);
    const [startX, setStartX] = useState(0);
    const [scrollLeft, setScrollLeft] = useState(0);

    const handleMouseDown = (e: React.MouseEvent) => {
        setIsDragging(true);
        setStartX(e.pageX - (scrollRef.current?.offsetLeft ?? 0));
        setScrollLeft(scrollRef.current?.scrollLeft ?? 0);
    };

    const handleMouseLeave = () => setIsDragging(false);
    const handleMouseUp = () => setIsDragging(false);

    const handleMouseMove = (e: React.MouseEvent) => {
        if (!isDragging || !scrollRef.current) return;
        e.preventDefault();
        const x = e.pageX - scrollRef.current.offsetLeft;
        const walk = (x - startX) * 1.5; // tốc độ kéo
        scrollRef.current.scrollLeft = scrollLeft - walk;
    };

    return (
        <div className="p-4 border border-gray-200 rounded-lg bg-white flex flex-col gap-4">
            {/* header */}
            <div className="text-lg font-semibold">
                Deals
            </div>

            {/* todo: custom hook grab an overflow-x list */}
            <div
                className="overflow-x-auto scrollbar-hidden cursor-grab active:cursor-grabbing"
                ref={scrollRef}
                onMouseDown={handleMouseDown}
                onMouseLeave={handleMouseLeave}
                onMouseUp={handleMouseUp}
                onMouseMove={handleMouseMove}
            >
                {/* coupons list */}
                <div className='flex gap-3 select-none'>
                    {[1, 2, 3, 4, 5].map((i) => (
                        <div
                            className='
                                bg-red-100 min-w-[220px] p-2 rounded-sm border border-red-200
                                flex justify-between items-center gap-2
                                relative'
                            key={i}
                        >
                            <div className='flex flex-col'>
                                <div className='flex items-center gap-1 text-[var(--dino-red-1)]'>
                                    <Ticket className='w-5 h-5' />
                                    <div className='text-base font-bold'>
                                        5% off
                                    </div>
                                </div>
                                <div className='text-xs'>
                                    On orders over ₫200K
                                </div>
                            </div>

                            <div className='
                                bg-[var(--dino-red-1)] text-white text-sm px-3.5 py-0.5 rounded-sm
                                flex justify-center items-center'
                            >
                                Claim
                            </div>

                            {/* a dashed divider */}
                            {/* <div className='
                                absolute left-[calc(100%-65px-8px*2)] top-1 bottom-1
                                border-l border-dashed border-red-300'/> */}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default ProductDeals;