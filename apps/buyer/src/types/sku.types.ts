import { TInventory } from "./inventory.types";

export enum SkuStatusType {
    LIVE = 'LIVE',
    DEACTIVATED = 'DEACTIVATED',
}
export type TSku = {
    id: string;
    inventory: TInventory;
    status: SkuStatusType;
    code: string;
    tierOptionIndexes: number[];
    tierOptionValue: string;
    retailPrice: number;
    productionCost: number | null;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}