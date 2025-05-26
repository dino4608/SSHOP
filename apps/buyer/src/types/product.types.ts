import { TCategory } from "./category.types";
import { TShop } from "./shop.types";
import { TSku } from "./sku.types";

export enum ProductStatusType {
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

export type TProduct = {
    id: string;
    status: ProductStatusType;
    name: string;
    slug: string;
    thumb: string;
    photos: string[];
    sizeGuidePhoto: string;
    video: string;
    retailPrice: number | null;
    minRetailPrice: number | null;
    maxRetailPrice: number | null;
    description: string;
    specifications: TProductSpecification[];
    tierVariations: TProductTierVariation[];
    meta: TProductMeta;
    skus: TSku[];
    category: TCategory;
    shop: TShop;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}

export type TProductBuyBox = Pick<TProduct,
    'id' | 'name' | 'shop' | 'skus' | 'tierVariations' | 'retailPrice' | 'minRetailPrice' | 'maxRetailPrice'>;

export type TProductPrice = Pick<TProductBuyBox,
    'retailPrice' | 'minRetailPrice' | 'maxRetailPrice' | 'skus'>;

export type TProductSelector = Pick<TProductBuyBox,
    'id' | 'skus' | 'tierVariations'>;

export type TProductBreadcrumb = Pick<TProduct,
    'name' | 'category'>;

export type TProductMedia = Pick<TProduct,
    'id' | 'thumb' | 'photos' | 'video' | 'sizeGuidePhoto'>;

export type TProductDescription = Pick<TProduct,
    'description' | 'specifications'>;

export type TProductItem = {
    id: string;
    name: string;
    status: ProductStatusType;
    meta: {
        isCodEnabled: boolean;
    };
    thumb: string;
    updatedAt: Date;
    retailPrice: number;
};