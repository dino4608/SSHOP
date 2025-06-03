export type TDiscountItem = {
    id: number;
    dealPrice: number;
    discountPercent: number;
};

export type TDiscount = {
    id: number;
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

