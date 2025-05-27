// helpers/product.helper.ts
import { TDiscountPrice } from "@/types/discount.type";
import { TProductPrice, TProductSelector } from "@/types/product.types";

export const applyPricing = (product: TProductPrice): {
    type:
    'FIXED_RETAIL_PRICE' | 'MULTI_RETAIL_PRICES' |
    'FIXED_DEAL_PRICE' | 'MULTI_DEAL_PRICES_FIXED_PERCENT' | 'FIXED_DEAL_PRICE_MULTI_PERCENTS',
    price: {
        main?: number,
        minMain?: number,
        maxMain?: number,
        side?: number,
        minSide?: number,
        maxSide?: number,
        percent?: number,
        minPercent?: number,
        maxPercent?: number
    }
} => {
    const { retailPrice, minRetailPrice, maxRetailPrice, discount } = product;
    const isRetail = Boolean(!discount);
    if (isRetail) {
        const isFixed = Boolean(retailPrice !== null);
        return isFixed ? {
            type: 'FIXED_RETAIL_PRICE',
            price: {
                main: retailPrice as number
            }
        } : {
            type: 'MULTI_RETAIL_PRICES',
            price: {
                minMain: minRetailPrice as number,
                maxMain: maxRetailPrice as number
            }
        };
    }

    const { dealPrice, minDealPrice, maxDealPrice, discountPercent, minDiscountPercent, maxDiscountPercent } = discount as TDiscountPrice;
    const isFixed = Boolean(dealPrice !== null && discountPercent !== null);
    return isFixed ? {
        type: 'FIXED_DEAL_PRICE',
        price: {
            main: dealPrice as number,
            side: retailPrice as number,
            percent: discountPercent as number
        }
    } : dealPrice === null ? {
        type: 'MULTI_DEAL_PRICES_FIXED_PERCENT',
        price: {
            minMain: minDealPrice as number,
            maxMain: maxDealPrice as number,
            minSide: minRetailPrice as number,
            maxSide: maxRetailPrice as number,
            percent: discountPercent as number
        }
    } : {
        type: 'FIXED_DEAL_PRICE_MULTI_PERCENTS',
        price: {
            main: dealPrice as number,
            minSide: minRetailPrice as number,
            maxSide: maxRetailPrice as number,
            minPercent: minDiscountPercent as number,
            maxPercent: maxDiscountPercent as number
        }
    };
}

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
