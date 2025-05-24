import { TApiDefinition } from "@/types/base.types";
import { HttpMethod, RESOURCES } from "@/lib/constants";
import { TDiscountedProduct } from "@/types/discounted-product.type";

export const discountedProductApi = {
    // BUYER PRIVATE //

    // QUERY //
    getByProductId: (productId: string): TApiDefinition<TDiscountedProduct> => ({
        route: `${RESOURCES.DISCOUNTED.PRIVATE}/${productId}`,
        method: HttpMethod.GET,
    }),
};