// src/types/cart.types.ts
import { TDiscountItem } from "./discount.type";
import { TProduct } from "./product.types";
import { TShop } from "./shop.types";
import { TSku } from "./sku.types";

// --- Side types picked from original types ---

export type TProductInCart = Pick<TProduct, 'id' | 'name' | 'thumb'>;

export type TSkuInCart = Pick<TSku, 'id' | 'code' | 'tierOptionIndexes' | 'tierOptionValue' | 'retailPrice'>;

export type TShopInCart = Pick<TShop, 'id' | 'name'>;

export type TDiscountItemInCart = Pick<TDiscountItem, 'dealPrice' | 'discountPercent'>;

// --- Main types of cart ---

export type TCartItem = {
    id: number
    quantity: number;
    photo: string;
    product: TProductInCart;
    sku: TSkuInCart;
    discountItem: TDiscountItemInCart | null;
};

export type TCartGroup = {
    id: number;
    shop: TShopInCart;
    cartItems: TCartItem[];
};

export type TCart = {
    id: number;
    total: number;
    cartGroups: TCartGroup[];
};

export type TUpdateQuantityBody = {
    cartItemId: number;
    quantity: number;
};

export type TRemoveCartItemBody = {
    cartItemIds: number[];
};