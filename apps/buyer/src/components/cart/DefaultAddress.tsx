// src/components/cart/DefaultAddress.tsx
"use client";
import { Button } from "@/components/ui/button"; // Import Button từ shadcn/ui
import { TAddress } from "@/types/address.types";
import { MapPinIcon } from 'lucide-react';

interface DefaultAddressProps {
    defaultAddress: TAddress | null;
}

export function DefaultAddress({ defaultAddress }: DefaultAddressProps) {
    const currentAddress = defaultAddress;

    return (
        currentAddress && <div className="bg-white p-4 rounded-sm border border-gray-200">
            <div className="flex justify-between items-center mb-2">
                <h2 className="text-base font-medium flex items-center gap-2">
                    Địa chỉ giao hàng
                </h2>
                <Button variant="link" className="p-0 h-auto text-blue-600 hover:no-underline text-sm">
                    Thay đổi
                </Button>
            </div>

            <div className="space-y-1">
                {/* <p className="text-gray-600 text-sm">
                    {currentAddress.contactName} - {currentAddress.contactPhone}
                </p> */}
                <p className="text-sm text-gray-600 flex items-start gap-1">
                    <MapPinIcon className="w-4 h-4 text-gray-600 flex-shrink-0 mt-0.5" />
                    <span>{currentAddress.street}, {currentAddress.commune}, {currentAddress.district}, {currentAddress.province}</span>
                </p>
            </div>
        </div>
    );
}