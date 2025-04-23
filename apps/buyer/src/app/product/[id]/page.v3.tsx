'use client';

import React, { useEffect, useRef, useState } from 'react';

type Props = {
    productId: string;
};

const ProductDetail = ({ productId }: Props) => {
    console.log(`ProductDetail rendered with productId: ${productId}`);

    const [isAtBottom, setIsAtBottom] = useState(false);
    // Create a ref for the sentinel element (or a transparent div at the bottom of the left column)
    // the useRef hook is a React hook that returns a mutable ref object
    // Its structure is: const sentinelRef: { current: HTMLDivElement | null }
    // It will persist for the full lifetime of the component
    // Initially, .current is set to null => the component mounts, the DOM commits => .current point to the actual DOM element
    // So, it can access the DOM element directly
    const sentinelRef = useRef<HTMLDivElement>(null);
    const rightColRef = useRef<HTMLDivElement>(null);

    // This is a "sticky-but-not-over-footer" effect that is the heart of the component
    // Detect the viewport at the bottom of the DOM tree
    // If it is at the bottom, set the right column to "absolute" position
    // If it is not at the bottom, set the right column to "sticky" position
    // As a result,
    // The right column will stick to the top of the viewport when scrolling down
    // The right column will be absolute when it reaches the bottom of the viewport
    // The right column will be sticky again when scrolling back up
    useEffect(() => {
        // Create an IntersectionObserver, a Web JS API to "listen" when an element appears in an region
        // If the element (or a entry) is appearing in the region, API entry.isIntersecting is true
        // This approach avoid the scroll event, so it is more effective and lighter
        const observer = new IntersectionObserver(
            // A callback function, which is linked to the observer:
            // Entries is a array of elements, which are registered to observe
            // But in this case, we only need to observe a first element (or entry[0])
            // If the entry is in the viewport, API isIntersecting is true, set isAtBottom to true
            (entries) => {
                const entry = entries[0];
                setIsAtBottom(entry.isIntersecting);
            },
            // Options:
            // Root: null: sign up the viewport as an observed region
            // Threshold: 0.1: sign up 10% of the observed element (a entry)
            {
                root: null,
                threshold: 0.1,
            }
        );

        // Register the observer to whether the sentinel element (the entry) is in the viewport (the root region) or not
        // sentinelRel.current points to the html div sentinel element, and is not null when the component is mounted
        const target = sentinelRef.current
        if (target) {
            observer.observe(target);
        }

        // Cleanup function:
        // When component unmounts, the observer will be unregistered
        return () => {
            if (target) {
                observer.unobserve(target);
            }
        };
    }, []); // the empty array makes this effect run at mounting phase and doesn't re-run
    // Summary:
    // The order running: component mounted -> useRef() init -> DOM committed -> .current is set to the DOM element ->
    // useEffect() -> observer.observe(sentinelRef.current) -> if isIntersecting is true, run the callback function

    return (
        <div className="min-h-screen bg-white">
            <div className="container mx-auto px-2 sm:px-10 lg:px-20 py-4 flex gap-6 relative">
                {/* Left Column */}
                <div className="w-3/5 space-y-6">
                    <div className="h-72 rounded-lg bg-red-400 flex items-center justify-center text-white font-bold text-xl">Product Image</div>
                    <div className="h-72 rounded-lg bg-yellow-400 flex items-center justify-center text-white font-bold text-xl">Estimated Delivery</div>
                    <div className="h-72 rounded-lg bg-green-400 flex items-center justify-center text-white font-bold text-xl">Entertainment</div>
                    <div className="h-72 rounded-lg bg-blue-400 flex items-center justify-center text-white font-bold text-xl">Deals</div>
                    <div className="h-72 rounded-lg bg-purple-400 flex items-center justify-center text-white font-bold text-xl">Bundles</div>
                    <div className="h-72 rounded-lg bg-pink-400 flex items-center justify-center text-white font-bold text-xl">Reviews</div>
                    <div className="h-72 rounded-lg bg-amber-400 flex items-center justify-center text-white font-bold text-xl">Shop Info</div>
                    <div className="h-72 rounded-lg bg-lime-400 flex items-center justify-center text-white font-bold text-xl">Description</div>
                    <div className="h-72 rounded-lg bg-sky-400 flex items-center justify-center text-white font-bold text-xl">Recommended Products</div>

                    {/* Sentinel to detect scroll bottom */}
                    <div ref={sentinelRef} className="h-10 bg-transparent"></div>
                </div>

                {/* Right Column */}
                <div
                    ref={rightColRef}
                    className={`w-2/5 space-y-4 transition-all duration-300 ${isAtBottom ? '' : 'sticky top-4 self-start'}`}
                >
                    <h1 className="text-2xl font-bold mb-2">Awesome Wireless Earbuds</h1>
                    <div className="text-gray-500 mb-4">⭐ 4.8 | 2.3K Sold | by GadgetStore</div>

                    <div className="mb-6">
                        <div className="text-2xl text-red-600 font-bold">$19.99</div>
                        <div className="line-through text-gray-400">$39.99</div>
                        <div className="text-sm text-green-600">50% OFF | You save $20.00</div>
                    </div>

                    <div className="space-y-3">
                        <button className="w-full py-2 bg-blue-100 text-blue-800 rounded-md font-semibold">Visit Shop</button>
                        <button className="w-full py-2 bg-purple-100 text-purple-800 rounded-md font-semibold">Chat</button>
                        <button className="w-full py-2 bg-green-100 text-green-800 rounded-md font-semibold">Add to Cart</button>
                        <button className="w-full py-2 bg-red-200 text-red-800 rounded-md font-semibold">Buy Now</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetail;