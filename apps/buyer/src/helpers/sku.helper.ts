import { TSku } from "@/types/sku.types";

export const findMatchingSku = (skus: TSku[], tierOptionIndexes: (number | null)[]): TSku | null => {
    return skus.find(sku =>
        sku.tierOptionIndexes.every((val, idx) => val === tierOptionIndexes[idx])
    ) as TSku;
}