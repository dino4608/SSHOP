// src/types/checkout.types.ts

// --- Base type of checkout ---
export type TCheckoutSummary = {
    itemsPrice: number;
    promotionAmount: number;
    shippingFee: number;
    payableAmount: number;
}

export type TCheckoutPricePromotion = {
    totalAmount: number;
    sellerDiscount: number;
    sellerCoupon: number;
    platformDiscount: number;
    platformCoupon: number;
}

export type TCheckoutShippingFee = {
    finalFee: number;
    initialFee: number;
    sellerShippingPromotion: number;
    platformShippingPromotion: number;
}

export type TCheckoutSnapshot = {
    summary: TCheckoutSummary;
    pricePromotion: TCheckoutPricePromotion;
    shippingFee: TCheckoutShippingFee;
}

// --- Specific type of checkout //
export type TEstimateCheckoutBody = {
    cartItemIds: number[];
};

export type TEstimateCheckout = {
    cartId: number;
    checkoutSnapshot: TCheckoutSnapshot;
};
