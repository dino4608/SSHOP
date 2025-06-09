import { TInventory } from "./inventory.types";

// NESTED TYPES //

export enum TSkuStatus {
    LIVE = 'LIVE',
    DEACTIVATED = 'DEACTIVATED',
}

// MAIN TYPES //

export type TSku = {
    id: number;
    inventory: TInventory;
    status: TSkuStatus;
    code: string;
    tierOptionIndexes: number[];
    tierOptionValue: string;
    retailPrice: number;
    productionCost: number | null;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}

// PICK TYPES //

export type TSkuLean = Pick<TSku,
    'id' | 'code' | 'tierOptionIndexes' | 'tierOptionValue'>;