export enum ShopStatusType {
    LACK_INFO = 'LACK_INFO',
    REVIEWING = 'REVIEWING',
    LIVE = 'LIVE',
    DEACTIVATED = 'DEACTIVATED',
    SUSPENDED = 'SUSPENDED',
    CLOSED = 'CLOSED',
    DELETED = 'DELETED',
}

export enum ShopBusinessType {
    INDIVIDUAL = 'INDIVIDUAL',
    HOUSEHOLD = 'HOUSEHOLD',
    ENTERPRISE = 'ENTERPRISE',
}

export type TShop = {
    id: string;
    status: ShopStatusType;
    shopName: string;
    shopCode: string;
    shopLogo: string;
    contactEmail: string;
    contactPhone: string | null;
    businessType: ShopBusinessType;
    sellerType: string | null;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}
