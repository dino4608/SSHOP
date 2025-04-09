import Image from "next/image";
import Link from "next/link";

const ProductItem = () => {
    return (
        <Link href="/product/1">
            <div className="w-full overflow-hidden rounded-lg bg-white shadow">
                {/* Product image */}
                <div className="relative aspect-square">
                    <Image
                        src={"/square.jpg"}
                        alt="Product Image"
                        fill={true}
                        className="object-over"
                        loading="lazy"
                    />
                </div>

                <div className="px-2 lg:px-4 py-2 lg:py-4 space-y-1 lg:space-y-2">
                    {/* Product title */}
                    <div className="text-sm lg:text-base text-gray-800 line-clamp-2 ">
                        Product title [Demo with a pro max plus mega super ultra extra long text]
                    </div>
                    {/* Product price */}
                    <div className="flex items-center space-x-1 lg:space-x-2">
                        <span className="text-lg lg:text-xl text-red-500 font-semibold ">
                            99.99₫
                        </span>
                        <span className="text-[12px] lg:text-sm text-gray-400 line-through ">
                            200.00₫
                        </span>
                    </div>
                    {/* Free shipping */}
                    <div className="text-[12px] lg:text-sm space-x-1 lg:space-x-2">
                        <span className="inline-flex items-center justify-center px-2 bg-teal-50 rounded-sm text-teal-500 font-semibold">
                            Free shipping
                        </span>
                        <span className="inline-flex items-center justify-center px-2 bg-white rounded-sm text-red-500 font-semibold border-1 border-red-500">
                            COD
                        </span>
                    </div>
                    {/* Stars and sales */}
                    <div className="text-[12px] lg:text-sm text-gray-400">
                        ⭐4.9 | 🔥600 sold
                    </div>
                </div>
            </div>
        </Link>
    );
};

export default ProductItem;