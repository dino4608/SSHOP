// src/components/cart/CartHydrator.tsx
"use client";
import { TAddress } from "@/types/address.types";
import { TCart } from "@/types/cart.types";
import { useCallback, useMemo, useState } from 'react';
import { CartDisplay } from "./CartDisplay";
import { CartSummary } from "./CartSummary";
import { DefaultAddress } from "./DefaultAddress";
import { useRouter } from "next/navigation";
import { toast } from "sonner";
import { clientFetch } from "@/lib/fetch/fetch.client";
import { api } from "@/lib/api";

interface CartHydratorProps {
    initialCart: TCart;
    initialDefaultAddress: TAddress | null;
}

export function CartHydrator({ initialCart, initialDefaultAddress }: CartHydratorProps) {
    const router = useRouter();

    // State để quản lý dữ liệu giỏ hàng trên client, cho phép optimistic updates
    const [cartData, setCartData] = useState<TCart>(initialCart);

    // State để lưu trữ danh sách các cartItem.id đã chọn
    const [selectedCartItemIds, setSelectedCartItemIds] = useState<Set<number>>(new Set());

    // Lấy tất cả các ID của item trong giỏ hàng (dùng useMemo để tối ưu)
    const allCartItemIds = useMemo(() => {
        const ids = new Set<number>();
        initialCart.cartGroups.forEach(group => {
            group.cartItems.forEach(item => ids.add(item.id));
        });
        return ids;
    }, [initialCart]);

    // Handler cho checkbox "Chọn tất cả"
    const handleCheckAll = useCallback((checked: boolean) => {
        if (checked) {
            setSelectedCartItemIds(new Set(allCartItemIds)); // Chọn tất cả
        } else {
            setSelectedCartItemIds(new Set()); // Bỏ chọn tất cả
        }
    }, [allCartItemIds]);

    // Handler cho checkbox của một nhóm
    const handleCheckGroup = useCallback((groupId: number, checked: boolean) => {
        const newSelectedIds = new Set(selectedCartItemIds);
        const groupItems = initialCart.cartGroups.find(g => g.id === groupId)?.cartItems || [];

        if (checked) {
            groupItems.forEach(item => newSelectedIds.add(item.id));
        } else {
            groupItems.forEach(item => newSelectedIds.delete(item.id));
        }
        setSelectedCartItemIds(newSelectedIds);
    }, [selectedCartItemIds, initialCart]);

    // Handler cho checkbox của một item
    const handleCheckItem = useCallback((itemId: number, checked: boolean) => {
        const newSelectedIds = new Set(selectedCartItemIds);
        if (checked) {
            newSelectedIds.add(itemId);
        } else {
            newSelectedIds.delete(itemId);
        }
        setSelectedCartItemIds(newSelectedIds);
    }, [selectedCartItemIds]);

    // Hàm xử lý optimistic update cho cập nhật số lượng (tương tự)
    // TODO: chưa check hàm handleQuantityUpdateOptimistic
    const handleQuantityUpdateOptimistic = useCallback(async (cartItemId: number, newQuantity: number) => {
        const previousCartData = cartData;
        const originalQuantity = cartData.cartGroups.flatMap(g => g.cartItems).find(item => item.id === cartItemId)?.quantity || 0;

        // Optimistic UI update: Cập nhật số lượng ngay lập tức
        setCartData(prevCart => {
            if (!prevCart) return prevCart;
            const newCartGroups = prevCart.cartGroups.map(group => ({
                ...group,
                cartItems: group.cartItems.map(item =>
                    item.id === cartItemId ? { ...item, quantity: newQuantity } : item
                )
            }));
            return { ...prevCart, cartGroups: newCartGroups };
        });

        const result = await clientFetch(api.carts.updateQuantity({ cartItemId, quantity: newQuantity }));

        if (!result.success) {
            console.error("Failed to update quantity:", result.error);
            toast.error(result.error || "Có lỗi xảy ra khi cập nhật số lượng.");
            setCartData(previousCartData); // Rollback
        } else {
            toast.success("Cập nhật số lượng thành công!");
        }
    }, [cartData]);

    // Hàm xử lý optimistic update cho xóa item
    const handleRemoveItemsOptimistically = useCallback(async (cartItemIdsToRemove: number[]) => {
        const prevCartData = cartData;
        const prevSelectedCartItemIds = new Set(selectedCartItemIds);

        // Optimistic UI update: Cập nhật state để xóa item ngay lập tức
        setCartData(prevCart => {
            if (!prevCart) return prevCart;

            const newCartGroups = prevCart.cartGroups.map(group => ({
                ...group,
                cartItems: group.cartItems.filter(item => !cartItemIdsToRemove.includes(item.id))
            })).filter(group => group.cartItems.length > 0);

            // Cập nhật selectedCartItemIds nếu item bị xóa đang được chọn
            setSelectedCartItemIds(prevSelected => {
                const newSelected = new Set(prevSelected);
                cartItemIdsToRemove.forEach(id => newSelected.delete(id));
                return newSelected;
            });

            return { ...prevCart, cartGroups: newCartGroups };
        });

        // Gọi API để xóa item
        const result = await clientFetch(api.carts.removeCartItems({ cartItemIds: cartItemIdsToRemove }));

        // Xử lý kết quả từ API
        if (!result.success || !result.data?.isDeleted) {
            toast.error(result.error || "Có lỗi xảy ra khi xóa sản phẩm.");
            setCartData(prevCartData);
            setSelectedCartItemIds(prevSelectedCartItemIds);
        } else {
            // TODO: revalidate cart
            toast.success(`Đã xóa ${result.data.count} sản phẩm thành công!`);
        }
    }, [cartData, selectedCartItemIds]);

    // Hàm để refresh dữ liệu giỏ hàng sau khi clientFetch thành công
    // Bạn có thể truyền hàm này xuống CartLineItem
    const refreshCartData = useCallback(() => {
        router.refresh(); // Buộc Next.js re-fetch dữ liệu cho trang hiện tại
    }, [router]);

    return (
        <div className="flex flex-col lg:flex-row gap-4">
            {/* Left area: Display Cart */}
            <div className="flex-1 lg:w-2/3">
                <CartDisplay
                    cart={cartData}
                    selectedCartItemIds={selectedCartItemIds}
                    onCheckAll={handleCheckAll}
                    onCheckGroup={handleCheckGroup}
                    onCheckItem={handleCheckItem}
                    onRemoveItem={(cartItemId) => handleRemoveItemsOptimistically([cartItemId])}
                    onQuantityUpdate={handleQuantityUpdateOptimistic}
                    onQuantityUpdateSuccess={refreshCartData}
                    onRemoveItemSuccess={refreshCartData}
                />
            </div>

            {/* Right area: Default address + Cart summary */}
            <div className="lg:w-1/3 flex flex-col gap-4 lg:sticky lg:top-4 lg:self-start">
                <DefaultAddress defaultAddress={initialDefaultAddress} />

                <CartSummary cart={initialCart} selectedCartItemIds={selectedCartItemIds} />
            </div>
        </div>
    );
}