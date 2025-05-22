// helpers/product.helper.ts
import { TProductSelector } from "@/types/product.types";

export const getDisabledOptionsMatrix = (product: TProductSelector, selected: (number | null)[]): boolean[][] => {
    const disabledMatrix: boolean[][] = product.tierVariations.map(tier =>
        tier.options.map(() => false)
    );

    const skus = product.skus;

    const isOptionDisabled = (tierIndex: number, optionIndex: number): boolean => {
        return skus
            .filter(sku => sku.tierOptionIndexes[tierIndex] === optionIndex)
            .every(sku => sku.inventory.stocks === 0);
    };

    product.tierVariations.forEach((tier, tierIndex) => {
        tier.options.forEach((_, optionIndex) => {
            const otherTierIndex = 1 - tierIndex;
            const selectedInOtherTier = selected[otherTierIndex];

            if (selectedInOtherTier === null) {
                disabledMatrix[tierIndex][optionIndex] = isOptionDisabled(tierIndex, optionIndex);
            } else {
                const matchedSkus = skus.filter(
                    sku =>
                        sku.tierOptionIndexes[tierIndex] === optionIndex &&
                        sku.tierOptionIndexes[otherTierIndex] === selectedInOtherTier
                );
                const allOutOfStock = matchedSkus.every(sku => sku.inventory.stocks === 0);
                disabledMatrix[tierIndex][optionIndex] = allOutOfStock;
            }
        });
    });

    return disabledMatrix;
};
