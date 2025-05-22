import { TProductDescription } from "@/types/product.types";
import { Dot } from "lucide-react";

type TProductDescriptionProps = {
    product: TProductDescription;
};

export const ProductDescription = ({ product }: TProductDescriptionProps) => {

    return (
        <div className="p-4 rounded-lg border border-gray-200 space-y-4">
            {/* Header */}
            <div className="text-lg font-semibold">Thông tin sản phẩm</div>

            {/* Specifications area */}
            {product.specifications && (
                <div className="space-y-4 text-sm">
                    <div className="text-gray-500 font-medium">Chi tiết</div>
                    <div className="leading-6">
                        {product.specifications.map(({ name, value }, index) => (
                            <div key={index} className="flex">
                                <div className="w-1/3 flex">
                                    <Dot />
                                    <span>{name}</span>
                                </div>
                                <div className="w-2/3">{value}</div>
                            </div>
                        ))}
                    </div>
                </div>
            )}

            {/* Description area */}
            {product.description && (
                <div className="space-y-4 text-sm">
                    <div className="text-gray-500 font-medium">Mô tả</div>
                    <div className="">
                        <p className="leading-6">
                            {product.description}
                        </p>
                    </div>
                </div>
            )}
        </div>
    );
};
