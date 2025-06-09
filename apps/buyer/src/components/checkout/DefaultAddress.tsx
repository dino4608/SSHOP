// src/components/checkout/DefaultAddress.tsx
"use client";
import { Button } from "@/components/ui/button";
import { useAppSelector } from "@/store/hooks";
import { ChevronRightIcon, MapPinIcon } from 'lucide-react';

export function DefaultAddress() {
    const currentAddress = useAppSelector(state => state.address.defaultAddress);;

    return (
        <section className="bg-white p-4 border border-gray-200">
            <div className="flex justify-between items-center">
                <h2 className="text-base font-medium flex items-center gap-2">
                    <MapPinIcon className="w-4 h-4" />
                    Địa chỉ giao hàng
                </h2>

                {!currentAddress ? (
                    <p className="text-gray-500">
                        Bạn hiện chưa có địa chỉ giao hàng.
                    </p>
                ) : (
                    <p className="text-gray-900 text-sm font-bold">
                        {currentAddress.contactName} ({currentAddress.contactPhone})
                        <span className="text-sm text-gray-600 font-normal ml-2">
                            {currentAddress.street}, {currentAddress.commune}, {currentAddress.district}, {currentAddress.province}
                        </span>
                    </p>
                )}

                <Button variant="link" className="p-0 has-[>svg]:px-0 h-auto text-gray-600 text-xs flex items-center gap-1">
                    Thay đổi
                    <ChevronRightIcon className="w-3 h-3" />
                </Button>
            </div>
        </section>
    );
}