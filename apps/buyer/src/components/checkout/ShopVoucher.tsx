// src/components/checkout/ShopVoucher.tsx
"use client";
import { ChevronRightIcon, TicketIcon } from "lucide-react";
import { Button } from "../ui/button";

export function ShopVoucher() {
    return (
        <div className="text-sm space-y-1 flex justify-end items-center">
            <Button variant="link" className="p-0 has-[>svg]:px-0 h-auto text-gray-600 flex items-center gap-1">
                <p className="text-gray-600 flex items-center gap-1">
                    <TicketIcon className="w-4 h-4 stroke-rose-500" />
                    Voucher từ cửa hàng
                </p>
                <ChevronRightIcon className="w-3 h-3" />
            </Button>
        </div>
    );
}