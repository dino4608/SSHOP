// src/app/cart/components/CartSummary.tsx
"use client";
import { TCart } from "@/types/cart.types";
import { useMemo } from "react";
import { Button } from "@/components/ui/button"; // Import Button từ shadcn/ui

interface CartSummaryProps {
    cart: TCart | null;
    selectedCartItemIds: Set<number>;
}

export function CartSummary({ cart, selectedCartItemIds }: CartSummaryProps) {
    const formatCurrency = (amount: number) => {
        return `₫${amount.toLocaleString('vi-VN')}`;
    };

    const subtotal = useMemo(() => {
        if (!cart) return 0;
        let total = 0;
        cart.cartGroups.forEach(group => {
            group.cartItems.forEach(item => {
                const itemPrice = item.discountItem ? item.discountItem.dealPrice : item.sku.retailPrice;
                total += itemPrice * item.quantity;
            });
        });
        return total;
    }, [cart]);

    const savings = useMemo(() => {
        if (!cart) return 0;
        let totalRetailPrice = 0;
        cart.cartGroups.forEach(group => {
            group.cartItems.forEach(item => {
                totalRetailPrice += item.sku.retailPrice * item.quantity;
            });
        });
        return totalRetailPrice - subtotal;
    }, [cart, subtotal]);


    return (
        <div className="bg-white p-4 rounded-sm border border-gray-200">
            <h2 className="text-base font-medium mb-3">Tổng kết giỏ hàng</h2>

            <div className="flex justify-between items-center text-sm text-gray-600 mb-4">
                <span>Tổng tiền hàng ({cart?.total || 0} sản phẩm)</span>
                <span className="font-medium">{formatCurrency(subtotal)}</span>
            </div>

            <div className="flex justify-between items-center text-sm text-green-600 mb-2">
                <span>Tiết kiệm</span>
                <span className="font-medium">- {formatCurrency(savings)}</span>
            </div>

            <div className="flex justify-between items-center text-lg font-semibold mb-4">
                <span>Tổng thanh toán</span>
                <span className="text-[var(--dino-red-1)]">{formatCurrency(subtotal)}</span>
            </div>

            <Button className="w-full bg-[var(--dino-red-1)] text-white py-3 rounded-lg font-semibold hover:opacity-90 transition-opacity">
                Thanh toán
            </Button>
        </div>
    );
}