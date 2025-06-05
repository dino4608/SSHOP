// src/lib/api/checkout.api.ts
import { TApiDefinition } from "@/types/base.types";
import { HttpMethod, RESOURCES } from "@/lib/constants";
import { TEstimateCheckoutBody, TEstimateCheckout } from "@/types/checkout.types";

export const checkoutApi = {
    // PUBLIC //

    // PRIVATE //

    // QUERY //
    estimateCheckout: (body: TEstimateCheckoutBody): TApiDefinition<TEstimateCheckout> => ({
        route: `${RESOURCES.CHECKOUT.PRIVATE}/estimate`,
        method: HttpMethod.POST,
        body,
        withAuth: true,
    }),
};
