export type TDiscountedSku = {
    id: string;
    discountedProductId: string;
    skuId: string;
};


export type TDiscountedProduct = {
    id: string;
    discountPercent: number;
    dealPrice: number | null;
    minDealPrice: number | null;
    maxDealPrice: number | null;
    totalLimit: number | null;
    buyerLimit: number | null;
    usedCount: number | null;
    usedBuyerIds: string[] | null;
    priceType: 'PRODUCT' | 'SKU';
    discountedSkus: TDiscountedSku[];
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
};