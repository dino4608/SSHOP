import { TApiDefinition } from "@/types/base.types";
import { HttpMethod, RESOURCES } from "@/lib/constants";
import { TDiscount } from "@/types/discount.type";

export const discountsApi = {
    // BUYER PUBLIC //

    // BUYER PRIVATE //

    // QUERY //
    getByProductId: (productId: string): TApiDefinition<TDiscount> => ({
        route: `${RESOURCES.DISCOUNTS.PRIVATE}/${productId}`,
        method: HttpMethod.GET,
    }),
};