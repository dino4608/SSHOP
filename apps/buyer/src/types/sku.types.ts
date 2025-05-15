import { TInventory } from "./inventory.types";

export enum SkuStatusType {
    LIVE = 'LIVE',
    DEACTIVATED = 'DEACTIVATED',
}
export type TSku = {
    id: string;
    inventory: TInventory;
    status: SkuStatusType;
    skuCode: string;
    tierIndex: number[];
    tierName: string;
    retailPrice: number;
    productionCost: number | null;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}