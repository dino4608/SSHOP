// src/components/checkout/CheckoutSummary.tsx
"use client";
import {
    Accordion,
    AccordionContent,
    AccordionItem,
    AccordionTrigger,
} from "@/components/ui/accordion";
import { Button } from "@/components/ui/button";
import { formatCurrency } from "@/lib/utils";
import { TCheckoutSnapshot } from "@/types/checkout.types";

interface CheckoutSummaryTotalProps {
    totalCheckoutSnapshot: TCheckoutSnapshot | null;
    handleConfirmOrder: () => Promise<void>;
    loadingCheckout: boolean;
    selectedPaymentMethod: string;
}

export function CheckoutSummary({
    totalCheckoutSnapshot: checkoutSnapshot,
    handleConfirmOrder,
    loadingCheckout,
    selectedPaymentMethod
}: CheckoutSummaryTotalProps) {

    return (
        <div className="bg-white p-4 border border-gray-200">
            <h2 className="text-base font-medium mb-2">Tổng kết giỏ hàng</h2>

            {/* Accordion cho chi tiết */}
            <Accordion type="single" collapsible className="w-full">
                <AccordionItem value="item-1" className="">
                    <AccordionTrigger className="p-0 pb-4 mb-4 gap-1 hover:no-underline text-sm font-normal text-gray-900 rounded-none border-b border-gray-200">
                        <div className="flex justify-between items-center text-gray-700 w-full">
                            <span>Tổng phụ</span>
                            <span className="font-medium">{formatCurrency(checkoutSnapshot?.summary.itemsPrice)}</span>
                        </div>
                    </AccordionTrigger>
                    <AccordionContent className="mb-4 space-y-4 text-sm border-b border-gray-200">
                        {/* Voucher */}
                        {checkoutSnapshot?.discountVoucher &&
                            checkoutSnapshot?.discountVoucher.totalVoucher !== 0 && (
                                <div className="">
                                    <div className="flex justify-between items-center">
                                        <span>Voucher giảm giá</span>
                                        <span className="">- {formatCurrency(checkoutSnapshot?.discountVoucher.totalVoucher || 0)}</span>
                                    </div>
                                    <div className="text-gray-600 ml-4 space-y-2 mt-2">
                                        {checkoutSnapshot?.discountVoucher.sellerVoucher !== 0 && (
                                            <div className="flex justify-between items-center text-gray-500">
                                                <span>Voucher từ cửa hàng</span>
                                                <span>- {formatCurrency(checkoutSnapshot?.discountVoucher.sellerVoucher || 0)}</span>
                                            </div>
                                        )}
                                        {checkoutSnapshot?.discountVoucher.platformVoucher !== 0 && (
                                            <div className="flex justify-between items-center text-gray-500">
                                                <span>Voucher từ DEAL</span>
                                                <span>- {formatCurrency(checkoutSnapshot?.discountVoucher.platformVoucher || 0)}</span>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            )}

                        {/* Shipping Fee */}
                        {checkoutSnapshot?.shippingFee && (
                            <div className="">
                                <div className="flex justify-between items-center text-gray-700">
                                    <span>Vận chuyển</span>
                                    {checkoutSnapshot?.shippingFee.finalFee === 0 ? (
                                        <span className="font-medium text-green-600">Freeship</span>
                                    ) : (
                                        <span className="font-medium">{formatCurrency(checkoutSnapshot?.shippingFee.finalFee)}</span>
                                    )}
                                </div>
                                <div className="text-gray-600 ml-4 space-y-2 mt-2">
                                    <div className="flex justify-between items-center text-gray-500">
                                        <span>Phí vận chuyển ban đầu</span>
                                        <span>{formatCurrency(checkoutSnapshot?.shippingFee.initialFee)}</span>
                                    </div>
                                    {checkoutSnapshot?.shippingFee.sellerShippingVoucher !== 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>Cửa hàng giảm phí vận chuyển</span>
                                            <span>- {formatCurrency(checkoutSnapshot?.shippingFee.sellerShippingVoucher || 0)}</span>
                                        </div>
                                    )}
                                    {checkoutSnapshot?.shippingFee.platformShippingVoucher !== 0 && (
                                        <div className="flex justify-between items-center text-gray-500">
                                            <span>DEAL giảm phí vận chuyển</span>
                                            <span>- {formatCurrency(checkoutSnapshot?.shippingFee.platformShippingVoucher || 0)}</span>
                                        </div>
                                    )}
                                </div>
                            </div>
                        )}
                    </AccordionContent>
                </AccordionItem>
            </Accordion>

            {/* Tiết kiệm */}
            <div className="flex justify-between items-center text-gray-700 text-sm mb-2">
                <span>Tiết kiệm</span>
                <span className="text-rose-500 font-medium">- {formatCurrency(checkoutSnapshot?.summary.savings)}</span>
            </div>

            {/* Tổng thanh toán */}
            <div className="flex justify-between items-center text-gray-800 text-lg font-semibold mb-2">
                <span>Tổng thanh toán</span>
                <span className="text-[var(--dino-red-1)]">{formatCurrency(checkoutSnapshot?.summary.total)}</span>
            </div>

            <Button
                className="w-full bg-[var(--dino-red-1)] text-white font-semibold hover:opacity-90 transition-opacity rounded-lg py-3 mt-4"
                onClick={handleConfirmOrder}
                disabled={loadingCheckout || !selectedPaymentMethod}
            >
                Đặt hàng
            </Button>
        </div>
    );
}
