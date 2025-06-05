// src/components/cart/CartSummary.tsx
"use client";
import {
    Accordion,
    AccordionContent,
    AccordionItem,
    AccordionTrigger,
} from "@/components/ui/accordion";
import { Button } from "@/components/ui/button";
import { TEstimateCheckout } from "@/types/checkout.types";
import { useMemo } from "react";

interface CartSummaryProps {
    selectedCartItemIds: Set<number>;
    estimateData: TEstimateCheckout | null;
}

export function CartSummary({ selectedCartItemIds, estimateData }: CartSummaryProps) {
    const formatCurrency = (amount: number) => {
        return `₫${amount.toLocaleString('vi-VN')}`;
    };

    // Tính toán tổng phụ dựa trên estimateData hoặc fallback về cách cũ nếu estimateData chưa có
    const subtotalDisplay = useMemo(() => {
        return estimateData?.checkoutSnapshot?.summary?.itemsPrice || 0;
    }, [estimateData]);

    // Tính toán tiết kiệm dựa trên estimateData
    const savingsDisplay = useMemo(() => {
        return estimateData?.checkoutSnapshot?.summary?.promotionAmount || 0;
    }, [estimateData]);

    const payableAmountDisplay = useMemo(() => {
        return estimateData?.checkoutSnapshot?.summary?.payableAmount || 0;
    }, [estimateData]);

    const finalShippingFeeDisplay = useMemo(() => {
        return estimateData?.checkoutSnapshot?.shippingFee?.finalFee || 0;
    }, [estimateData]);

    // Đếm số lượng sản phẩm đã chọn
    const selectedItemCount = selectedCartItemIds.size;

    return (
        <div className="bg-white p-4 rounded-sm border border-gray-200">
            <h2 className="text-base font-medium mb-2">Tổng kết giỏ hàng</h2>

            {/* Accordion cho chi tiết */}
            <Accordion type="single" collapsible className="w-full">
                <AccordionItem value="item-1" className="">
                    <AccordionTrigger className="p-0 pb-4 mb-4 hover:no-underline text-sm font-normal text-gray-900 rounded-none border-b">
                        <div className="flex justify-between items-center w-full">
                            <span>Tổng phụ</span>
                            <span className="font-medium">{formatCurrency(subtotalDisplay)}</span>
                        </div>
                    </AccordionTrigger>
                    <AccordionContent className="mb-4 space-y-4 text-sm border-b">
                        {/* Khuyến mãi */}
                        {estimateData?.checkoutSnapshot?.pricePromotion && (
                            <div className="">
                                <div className="flex justify-between items-center">
                                    <span>Khuyến mãi</span>
                                    <span className="">- {formatCurrency(estimateData?.checkoutSnapshot?.pricePromotion?.totalAmount || 0)}</span>
                                </div>
                                <div className="text-gray-600 ml-4 space-y-1 mt-1">
                                    {estimateData?.checkoutSnapshot?.pricePromotion?.sellerDiscount != 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>Giảm giá từ cửa hàng</span>
                                            <span>- {formatCurrency(estimateData?.checkoutSnapshot?.pricePromotion?.sellerDiscount || 0)}</span>
                                        </div>
                                    )}
                                    {estimateData?.checkoutSnapshot?.pricePromotion?.sellerCoupon !== 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>Coupon từ cửa hàng</span>
                                            <span>- {formatCurrency(estimateData?.checkoutSnapshot?.pricePromotion?.sellerCoupon || 0)}</span>
                                        </div>
                                    )}
                                    {estimateData?.checkoutSnapshot?.pricePromotion?.platformDiscount !== 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>Giảm giá từ DEAL</span>
                                            <span>- {formatCurrency(estimateData?.checkoutSnapshot?.pricePromotion?.platformDiscount || 0)}</span>
                                        </div>
                                    )}
                                    {estimateData?.checkoutSnapshot?.pricePromotion?.platformCoupon !== 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>Coupon từ DEAL</span>
                                            <span>- {formatCurrency(estimateData?.checkoutSnapshot?.pricePromotion?.platformCoupon || 0)}</span>
                                        </div>
                                    )}
                                </div>
                            </div>
                        )}

                        {/* Vận chuyển */}
                        {estimateData?.checkoutSnapshot?.shippingFee && (
                            <div className="">
                                <div className="flex justify-between items-center">
                                    <span>Vận chuyển</span>
                                    {estimateData.checkoutSnapshot.shippingFee.finalFee === 0 ? (
                                        <span className="font-medium text-green-600">Freeship</span>
                                    ) : (
                                        <span className="font-medium">{formatCurrency(estimateData.checkoutSnapshot.shippingFee.finalFee)}</span>
                                    )}
                                </div>
                                <div className="text-gray-600 ml-4 space-y-1 mt-1">
                                    <div className="flex justify-between items-center text-gray-500">
                                        <span>Phí vận chuyển ban đầu</span>
                                        <span>{formatCurrency(estimateData.checkoutSnapshot.shippingFee.initialFee)}</span>
                                    </div>
                                    {estimateData.checkoutSnapshot.shippingFee.sellerShippingPromotion !== 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>Cửa hàng giảm phí vận chuyển</span>
                                            <span>- {formatCurrency(estimateData.checkoutSnapshot.shippingFee.sellerShippingPromotion || 0)}</span>
                                        </div>
                                    )}
                                    {estimateData.checkoutSnapshot.shippingFee.platformShippingPromotion !== 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>DEAL giảm phí vận chuyển</span>
                                            <span>- {formatCurrency(estimateData.checkoutSnapshot.shippingFee.platformShippingPromotion || 0)}</span>
                                        </div>
                                    )}
                                </div>
                            </div>
                        )}
                    </AccordionContent>
                </AccordionItem>
            </Accordion>

            {/* Tiết kiệm */}
            <div className="flex justify-between items-center text-sm mb-1">
                <span>Tiết kiệm</span>
                <span className="text-green-600 font-medium">- {formatCurrency(savingsDisplay)}</span>
            </div>

            {/* Tổng thanh toán */}
            <div className="flex justify-between items-center text-lg font-semibold mb-2">
                <span>Tổng thanh toán</span>
                <span className="text-[var(--dino-red-1)]">{formatCurrency(payableAmountDisplay)}</span>
            </div>

            {/* Phí vận chuyển */}
            {/* <div className="flex justify-between items-center text-sm text-gray-600 mb-1">
                <span>Phí vận chuyển</span>
                {finalShippingFeeDisplay === 0 ? (
                    <span className="font-medium text-green-600">Freeship</span>
                ) : (
                    <span className="font-medium">{formatCurrency(finalShippingFeeDisplay)}</span>
                )}
            </div> */}



            <Button
                className="w-full bg-[var(--dino-red-1)] text-white py-3 rounded-lg font-semibold hover:opacity-90 transition-opacity mt-4"
                disabled={selectedItemCount === 0}
            >
                Thanh toán ({selectedItemCount} chọn)
            </Button>
        </div>
    );
}
