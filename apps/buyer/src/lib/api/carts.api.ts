// src/api/carts.api.ts
import { TApiDefinition, TDeletedRes } from "@/types/base.types";
import { HttpMethod, RESOURCES } from "@/lib/constants";
import { TCart, TCartItem, TRemoveCartItemBody, TUpdateQuantityBody } from "@/types/cart.types";

export const cartsApi = {
    // PUBLIC //

    // PRIVATE //

    // QUERY //
    getCart: (): TApiDefinition<TCart> => ({
        route: `${RESOURCES.CARTS.PRIVATE}/get`,
        method: HttpMethod.GET,
        withAuth: true,
    }),
    // COMMAND //
    updateQuantity: (body: TUpdateQuantityBody): TApiDefinition<TCartItem> => ({
        route: `${RESOURCES.CARTS.PRIVATE}/items/quantity/update`,
        method: HttpMethod.PATCH,
        body,
        withAuth: true,
    }),
    removeCartItems: (body: TRemoveCartItemBody): TApiDefinition<TDeletedRes> => ({
        route: `${RESOURCES.CARTS.PRIVATE}/items/remove`,
        method: HttpMethod.DELETE,
        body,
        withAuth: true,
    }),
};