export type TDiscountItem = {
    id: string;
    discountedProductId: string;
    skuId: string;
};

export type TDiscount = {
    id: string;
    dealPrice: number | null;
    minDealPrice: number | null;
    maxDealPrice: number | null;
    discountPercent: number;
    minDiscountPercent: number | null;
    maxDiscountPercent: number | null;
    totalLimit: number | null;
    buyerLimit: number | null;
    usedCount: number | null;
    usedBuyerIds: string[] | null;
    levelType: 'PRODUCT' | 'SKU';
    discountItems: TDiscountItem[];
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
};

export type TDiscountPrice = Pick<TDiscount,
    'dealPrice' | 'minDealPrice' | 'maxDealPrice' | 'discountPercent' | 'minDiscountPercent' | 'maxDiscountPercent'>;