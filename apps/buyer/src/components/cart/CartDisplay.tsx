// src/components/cart/CartDisplay.tsx
"use client";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { api } from "@/lib/api";
import { RESOURCES } from "@/lib/constants";
import { clientFetch } from "@/lib/fetch/fetch.client";
import { getFileUrl } from "@/lib/files";
import { formatPercent } from "@/lib/utils";
import { TCart, TCartGroup, TCartItem } from "@/types/cart.types";
import { SquarePenIcon, StoreIcon, Ticket, Trash2Icon as TrashIcon } from 'lucide-react';
import Link from "next/link";
import { useMemo, useState } from "react";
import { toast } from "sonner";

// --- CartDisplay Component ---
interface CartDisplayProps {
    cart: TCart;
    selectedCartItemIds: Set<number>;
    onCheckAll: (checked: boolean) => void;
    onCheckGroup: (groupId: number, checked: boolean) => void;
    onCheckItem: (itemId: number, checked: boolean) => void;
    onRemoveItem: (cartItemId: number) => Promise<void>;
    onQuantityUpdate: (cartItemId: number, newQuantity: number) => Promise<void>;
    onQuantityUpdateSuccess: () => void;
    onRemoveItemSuccess: () => void;
}

export function CartDisplay({
    cart, selectedCartItemIds,
    onCheckAll, onCheckGroup, onCheckItem, onRemoveItem, onQuantityUpdate, onQuantityUpdateSuccess, onRemoveItemSuccess
}: CartDisplayProps) {
    const formatCurrency = (amount: number) => {
        return `₫${amount.toLocaleString('vi-VN')}`;
    };

    const allCartItemIds = useMemo(() => {
        const ids = new Set<number>();
        cart.cartGroups.forEach(group => {
            group.cartItems.forEach(item => ids.add(item.id));
        });
        return ids;
    }, [cart]);

    const isAllSelected = useMemo(() => {
        if (allCartItemIds.size === 0) return false;
        return allCartItemIds.size === selectedCartItemIds.size &&
            Array.from(allCartItemIds).every(id => selectedCartItemIds.has(id));
    }, [allCartItemIds, selectedCartItemIds]);


    return (
        <div className="bg-white">
            {/* Table Header */}
            <div className="grid grid-cols-cart-header-layout gap-4 text-sm text-gray-600 bg-white p-3 rounded-sm border border-gray-200">
                {/* Checkbox of all */}
                <div className="flex items-center">
                    <Checkbox
                        id="select-all-cart"
                        className="w-4 h-4"
                        checked={isAllSelected}
                        onCheckedChange={(checkedState) => {
                            if (typeof checkedState === 'boolean') {
                                onCheckAll(checkedState);
                            }
                        }}
                    />
                </div>
                <span className="text-left">Sản phẩm</span>
                <span className="text-center">Đơn giá</span>
                <span className="text-center">Số lượng</span>
                <span className="text-center">Thành tiền</span>
                {/* Actions (active edit mode) */}
                <div className="text-center text-sm">
                    <Button variant="ghost" size="icon" className="h-4 text-gray-500 hover:text-red-600">
                        <SquarePenIcon className="w-4 h-4" />
                    </Button>
                </div>
            </div>

            {/* Cart Groups */}
            {cart.cartGroups.map(group => (
                <CartGroup
                    key={group.id}
                    group={group}
                    formatCurrency={formatCurrency}
                    selectedCartItemIds={selectedCartItemIds}
                    onCheckGroup={onCheckGroup}
                    onCheckItem={onCheckItem}
                    onQuantityUpdate={onQuantityUpdate}
                    onRemoveItem={onRemoveItem}
                />
            ))}
        </div>
    );
}

// --- CartGroup Component ---
interface CartGroupProps {
    group: TCartGroup;
    formatCurrency: (amount: number) => string;
    selectedCartItemIds: Set<number>;
    onCheckGroup: (groupId: number, checked: boolean) => void;
    onCheckItem: (itemId: number, checked: boolean) => void;
    onQuantityUpdate: (cartItemId: number, newQuantity: number) => Promise<void>;
    onRemoveItem: (cartItemId: number) => Promise<void>;
}

const CartGroup = ({
    group, formatCurrency, selectedCartItemIds, onCheckGroup, onCheckItem, onQuantityUpdate, onRemoveItem
}: CartGroupProps) => {
    const isGroupSelected = useMemo(() => {
        if (group.cartItems.length === 0) return false;
        return group.cartItems.every(item => selectedCartItemIds.has(item.id));
    }, [group.cartItems, selectedCartItemIds]);

    return (
        <div className="border border-gray-200 rounded-sm mt-4 overflow-hidden">
            {/* Header of CartGroup */}
            <div className="p-3 flex items-center gap-3 border-b border-gray-200">
                {/* Checkbox of a group */}
                <div className="flex items-center flex-shrink-0">
                    <Checkbox
                        id={`select-group-${group.id}`}
                        className="w-4 h-4"
                        checked={isGroupSelected}
                        onCheckedChange={(checkedState) => {
                            if (typeof checkedState === 'boolean') {
                                onCheckGroup(group.id, checkedState);
                            }
                        }}
                    />
                </div>
                {/* Shop name */}
                <Link
                    href={`/shops/${group.shop.id}`}
                    className="flex items-center hover:text-blue-600 font-medium text-base gap-2"
                >
                    <StoreIcon className="w-4 h-4" />
                    {group.shop.name}
                </Link>
                {/* Actions (Base UI) */}
                <div className="ml-auto flex gap-3">
                    <Button variant="ghost" size="sm" className="flex items-center text-gray-700 hover:text-blue-600 text-sm h-auto px-2 py-1">
                        <Ticket className="w-4 h-4 mr-1" /> Coupon
                    </Button>
                </div>
            </div>

            {/* Body: List of CartItems */}
            <div>
                {group.cartItems.map(item => (
                    <CartLineItem
                        key={item.id}
                        item={item}
                        formatCurrency={formatCurrency}
                        selectedCartItemIds={selectedCartItemIds}
                        onCheckItem={onCheckItem}
                        onQuantityUpdate={onQuantityUpdate}
                        onRemoveItem={onRemoveItem}
                    />
                ))}
            </div>
        </div>
    );
};

// --- CartLineItem Component ---
interface CartLineItemProps {
    item: TCartItem;
    formatCurrency: (amount: number) => string;
    selectedCartItemIds: Set<number>;
    onCheckItem: (itemId: number, checked: boolean) => void;
    onRemoveItem: (cartItemId: number) => Promise<void>;
    onQuantityUpdate: (cartItemId: number, newQuantity: number) => Promise<void>;
}

const CartLineItem = ({
    item, formatCurrency, selectedCartItemIds, onCheckItem, onRemoveItem, onQuantityUpdate
}: CartLineItemProps) => {
    const [quantity, setQuantity] = useState<number>(item.quantity);

    const itemPrice = item.discountItem ? item.discountItem.dealPrice : item.sku.retailPrice;
    const subtotal = itemPrice * quantity;

    const productImageUrl = getFileUrl(item.photo, RESOURCES.PRODUCTS.BASE, item.product.id);

    const isItemSelected = selectedCartItemIds.has(item.id);

    const onChangeQuantity = async (cartItemId: number, delta: number) => {
        // Optimistic update
        const prev = quantity;

        const update = Math.max(1, Math.min(200, prev + delta));
        if (update === quantity) return;

        setQuantity(update);

        const result = await clientFetch(api.carts.updateQuantity({ cartItemId, quantity: update }));
        if (result.success === false) {
            setQuantity(prev);
            toast.error(result.error);
        } else {
            // TODO: revalidation cart after update
            // After clientFetch success, need to refresh/revalidate data.
            // router refresh() or store dispatch().
            // onQuantityUpdateSuccess();
            // toast.success("Cập nhật số lượng thành công!");
        };
    };

    return (
        <div className="grid grid-cols-cart-item-layout items-center gap-4 py-4 px-3 border-b border-gray-100 last:border-b-0">
            {/* Checkbox of an item*/}
            <div className="flex items-center flex-shrink-0">
                <Checkbox
                    id={`select-item-${item.id}`}
                    className="w-4 h-4"
                    checked={isItemSelected}
                    // Sửa lỗi: Kiểm tra kiểu của 'checked'
                    onCheckedChange={(checkedState) => {
                        if (typeof checkedState === 'boolean') {
                            onCheckItem(item.id, checkedState);
                        }
                    }}
                />
            </div>

            {/* Product - Sku - CartItem */}
            <div className="flex items-center gap-3">
                <Link href={`/product/${item.product.id}`} className="block flex-shrink-0">
                    <img src={productImageUrl} alt={item.product.name} className="w-20 h-20 object-cover rounded cursor-pointer" />
                </Link>
                <div className="">
                    <Link href={`/product/${item.product.id}`}>
                        <p className="text-[13px] text-gray-600 line-clamp-2 hover:text-blue-600 cursor-pointer">{item.product.name}</p>
                    </Link>
                    <p className="mt-1 text-[13px] inline-flex justify-center items-center text-gray-800 bg-gray-100 rounded-[3px] p-1">{item.sku.tierOptionValue}</p>

                </div>
            </div>

            {/* Unit price */}
            <div className="text-center text-gray-800">
                {item.discountItem ? (
                    <>
                        <p className="font-semibold text-red-500">{formatCurrency(item.discountItem.dealPrice)}</p>
                        <p className="text-xs text-gray-400 line-through">{formatCurrency(item.sku.retailPrice)}
                            <span className='ml-1 inline-flex justify-center items-center text-[11px] text-red-500 bg-red-100 rounded-sm p-0.5'>
                                {formatPercent(item.discountItem.discountPercent)}
                            </span>
                        </p>
                    </>
                ) : (
                    <p className="font-semibold">{formatCurrency(item.sku.retailPrice)}</p>
                )}
            </div>

            {/* Quantity */}
            <div className="text-center">
                <div className="flex items-center justify-center border border-gray-300 rounded overflow-hidden w-24 mx-auto">
                    <Button
                        onClick={() => onChangeQuantity(item.id, -1)}
                        variant="ghost" size="sm" className="px-2 py-1 h-auto rounded-none">-</Button>
                    <Input
                        type="text"
                        value={quantity}
                        readOnly
                        className="w-10 text-center border-x-0 focus-visible:ring-0 focus-visible:ring-offset-0 rounded-none h-auto py-1" />
                    <Button
                        onClick={() => onChangeQuantity(item.id, 1)}
                        variant="ghost" size="sm" className="px-2 py-1 h-auto rounded-none">+</Button>
                </div>
            </div>

            {/* Total amount */}
            <div className="text-center font-semibold text-red-500">
                {formatCurrency(subtotal)}
            </div>

            {/* Actions (remove icon) */}
            <div className="text-center text-sm">
                <Button
                    onClick={() => onRemoveItem(item.id)}
                    variant="ghost" size="icon" className="text-gray-500 hover:text-red-600">
                    <TrashIcon className="w-5 h-5" />
                </Button>
            </div>
        </div>
    );
};