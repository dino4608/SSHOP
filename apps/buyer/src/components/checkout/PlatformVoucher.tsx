// src/components/checkout/CheckoutVoucherSection.tsx
"use client";
import { Button } from "@/components/ui/button";
import { ChevronRightIcon, TicketIcon } from "lucide-react";

export function PlatformVoucher() {
    return (
        <section className="bg-white p-4 border border-gray-200 flex justify-between items-center">
            <div className="flex items-center gap-2 text-base font-medium">
                <TicketIcon
                    className="w-5 h-5 fill-[var(--dino-red-1)] text-[var(--dino-red-1)]"
                />
                Voucher từ DEAL
            </div>
            <Button variant="link" className="p-0 has-[>svg]:px-0 h-auto text-gray-600 text-xs flex items-center justify-end gap-1">
                Nhận ngay
                <ChevronRightIcon className="w-3 h-3" />
            </Button>
        </section>
    );
}