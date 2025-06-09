// src/types/checkout.types.ts

import { TDraftOrder } from "./order.types";

// NESTED TYPES //

export type TCheckoutSummary = {
    itemsPrice: number;
    savings: number;
    shippingFee: number;
    total: number;
}

export type TDiscountVoucher = {
    totalVoucher: number;
    sellerVoucher: number;
    platformVoucher: number;
}

export type TCheckoutShippingFee = {
    finalFee: number;
    initialFee: number;
    sellerShippingVoucher: number;
    platformShippingVoucher: number;
}

export type TCheckoutSnapshot = {
    summary: TCheckoutSummary;
    discountVoucher: TDiscountVoucher;
    shippingFee: TCheckoutShippingFee;
}

// MAIN TYPES //

export type TEstimateCheckout = {
    cartId: number;
    checkoutSnapshot: TCheckoutSnapshot;
};

export type TEstimateCheckoutBody = {
    cartItemIds: number[];
};

export type TStartCheckout = {
    checkoutId: number;
    totalCheckoutSnapshot: TCheckoutSnapshot;
    orders: TDraftOrder[];
};

export type TStartCheckoutBody = {
    cartItemIds: number[];
};

export type TConfirmCheckout = {
    isConfirmed: boolean;
    count: number;
};

export type TConfirmCheckoutBody = {
    orderIds: number[];
};
