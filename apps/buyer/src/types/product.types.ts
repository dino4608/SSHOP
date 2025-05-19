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
    options: string[];
    photos: string[];
}

export type TProductMeta = {
    isCodEnabled: boolean;
}

export type TProduct = {
    id: string;
    shop: TShop;
    category: TCategory;
    skus: TSku[];
    status: ProductStatusType;
    name: string;
    description: string;
    slug: string;
    thumb: string;
    photos: string[];
    video: string;
    sizeChart: string;
    retailPrice: number;
    specifications: TProductSpecification[];
    tierVariations: TProductTierVariation[];
    meta: TProductMeta;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}

export type TProductActions = Pick<TProduct,
    'id' | 'name' | 'shop' | 'skus' | 'retailPrice' | 'tierVariations'>;

export type TProductBreadcrumb = Pick<TProduct,
    'name' | 'category'>;

export type TProductMedia = Pick<TProduct,
    'id' | 'thumb' | 'photos' | 'video' | 'sizeChart'>;

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