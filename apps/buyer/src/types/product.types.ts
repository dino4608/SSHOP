import { TCategory } from "./category.types";
import { TDiscount } from "./discount.type";
import { TShop } from "./shop.types";
import { TSku } from "./sku.types";

// NESTED TYPES //

export enum TProductStatus {
    DRAFT = 'DRAFT',
    REVIEWING = 'REVIEWING',
    LIVE = 'LIVE',
    DEACTIVATED = 'DEACTIVATED',
    SUSPENDED = 'SUSPENDED',
    DELETED = 'DELETED',
}

export type TProductSpecification = {
    name: string;
    value: string;
}

export type TProductTierVariation = {
    name: string;
    options: {
        value: string;
        photo: string | null;
    }[];
}

export type TProductMeta = {
    isCodEnabled: boolean;
}

// MAIN TYPES //

export type TProduct = {
    id: number;
    status: TProductStatus;
    name: string;
    slug: string;
    thumb: string;
    photos: string[];
    sizeGuidePhoto: string;
    video: string;
    retailPrice: number | null;
    minRetailPrice: number | null;
    maxRetailPrice: number | null;
    dealPrice: number | null;
    discountPercent: number | null;
    description: string;
    specifications: TProductSpecification[];
    tierVariations: TProductTierVariation[];
    meta: TProductMeta;
    skus: TSku[];
    category: TCategory;
    shop: TShop;
    discount: TDiscount | null;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}

// PICK TYPES //

export type TProductItem = Pick<TProduct,
    'id' | 'name' | 'thumb' | 'meta' | 'retailPrice' | 'dealPrice' | 'discountPercent'>;

export type TProductBuyBox = Pick<TProduct,
    'id' | 'name' | 'shop' | 'skus' | 'tierVariations' | 'retailPrice' | 'minRetailPrice' | 'maxRetailPrice' | 'discount'>;

export type TProductPrice = Pick<TProductBuyBox,
    'retailPrice' | 'minRetailPrice' | 'maxRetailPrice' | 'discount'>;

export type TProductSelector = Pick<TProductBuyBox,
    'id' | 'skus' | 'tierVariations'>;

export type TProductBreadcrumb = Pick<TProduct,
    'name' | 'category'>;

export type TProductMedia = Pick<TProduct,
    'id' | 'thumb' | 'photos' | 'video' | 'sizeGuidePhoto'>;

export type TProductDescription = Pick<TProduct,
    'description' | 'specifications'>;

export type TProductLean = Pick<TProduct,
    'id' | 'name' | 'thumb'>;