// helpers/product.helper.ts
import { TDiscountPrice } from "@/types/discount.type";
import { TProductPrice, TProductSelector } from "@/types/product.types";

export const applyPricing = (product: TProductPrice, discount: TDiscountPrice | null) => {
    const { retailPrice, minRetailPrice, maxRetailPrice } = product;
    const isDiscounted = Boolean(discount);
    if (!isDiscounted) {
        const isSingleRetailPrice = Boolean(minRetailPrice === maxRetailPrice);
        return isSingleRetailPrice ? {
            type: 'SINGLE_RETAIL_PRICE',
            price: {
                retail: retailPrice || minRetailPrice
            }
        } : {
            type: 'MULTI_RETAIL_PRICES',
            price: {
                minRetail: minRetailPrice,
                maxRetail: maxRetailPrice
            }
        };
    }

    const { dealPrice, minDealPrice, maxDealPrice, discountPercent, minDiscountPercent, maxDiscountPercent } = discount as TDiscountPrice;
    const isFixedPrice = Boolean(dealPrice !== null);
    if (isFixedPrice) {
        const isSinglePercent = Boolean(minDiscountPercent === maxDiscountPercent);
        return isSinglePercent ? {
            type: 'SINGLE_DEAL_PRICE_SINGLE_PERCENT',
            price: {
                deal: dealPrice as number,
                percent: (1 - (dealPrice as number) / (retailPrice !== null ? retailPrice as number : minRetailPrice as number)) * 100
            }
        } : {
            type: 'SINGLE_DEAL_PRICE_MULTI_PERCENTS',
            price: {
                deal: dealPrice as number,
                minPercent: minDiscountPercent as number,
                maxDealPrice: maxDiscountPercent as number
            }
        };
    }

    const isSingleDealPrice = Boolean(minDealPrice === maxDealPrice);
    return isSingleDealPrice ? {
        type: 'SINGLE_DEAL_PRICE_SINGLE_PERCENT',
        price: {
            deal: (retailPrice !== null ? retailPrice as number : minRetailPrice as number) as number * (1 - discountPercent / 100),
            percent: discountPercent
        }
    } : {
        type: 'MULTI_DEAL_PRICES_SINGLE_PERCENT',
        price: {
            minDeal: minDealPrice as number,
            maxDeal: maxDealPrice as number,
            percent: discountPercent
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
