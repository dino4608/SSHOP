// src/components/checkout/ShippingInfo.tsx
"use client";
import { TCheckoutShippingFee } from "@/types/checkout.types";
import { TShippingDetail } from "@/types/order.types";
import { formatCurrency } from "@/lib/utils"; // Assuming formatCurrency utility
import { PackageCheckIcon, TruckIcon } from "lucide-react";

interface ShippingInfoProps {
    shippingDetail: TShippingDetail;
    shippingFee: TCheckoutShippingFee;
}

export function ShippingInfo({ shippingDetail, shippingFee }: ShippingInfoProps) {
    const formatDeliveryEstimate = (min: Date, max: Date) => {
        const options: Intl.DateTimeFormatOptions = { day: 'numeric', month: 'short' };
        const minDate = new Date(min);
        const maxDate = new Date(max);

        const sameMonth = minDate.getMonth() === maxDate.getMonth() && minDate.getFullYear() === maxDate.getFullYear();

        if (sameMonth) {
            const dayRange = `${minDate.getDate()} - ${maxDate.getDate()}`;
            const month = maxDate.toLocaleDateString('vi-VN', { month: 'short' });
            return `${dayRange} ${month}`;
        } else {
            return `${minDate.toLocaleDateString('vi-VN', options)} - ${maxDate.toLocaleDateString('vi-VN', options)}`;
        }
    };

    return (
        <div className="text-sm space-y-1 flex justify-between">
            <p className="text-gray-600 flex items-center gap-1">
                {/* <TruckIcon className="w-5 h-5 fill-green-500 stroke-green-500 border border-green-500 rounded-sm p-[2px]" /> */}
                <TruckIcon className="w-4 h-4 stroke-green-500" />
                Vận chuyển bởi <span className="text-gray-800">{shippingDetail.carrier}</span>
            </p>

            {shippingDetail.minEstimatedDelivery && shippingDetail.maxEstimatedDelivery && (
                <p className="text-gray-600 flex items-center gap-1">
                    <PackageCheckIcon className="w-4 h-4 stroke-green-500" />
                    Dự kiện giao vào <span className="font-medium text-green-600">
                        {formatDeliveryEstimate(shippingDetail.minEstimatedDelivery, shippingDetail.maxEstimatedDelivery)}
                    </span>
                </p>
            )}

            <div className="flex justify-end items-center gap-1">
                {shippingFee.initialFee !== shippingFee.finalFee && shippingFee.initialFee > 0 && (
                    <span className="text-sm text-gray-400 line-through">{formatCurrency(shippingFee.initialFee)}</span>
                )}
                {shippingFee.finalFee === 0 ? (
                    <span className="font-bold text-green-600">Freeship</span>
                ) : (
                    <span className="font-bold text-green-600">{formatCurrency(shippingFee.finalFee)}</span>
                )}
            </div>
        </div>
    );
}