import { TApiDefinition, TPageRes } from "@/types/base.types";
import { TProduct, TProductItem } from "@/types/product.types";
import { HttpMethod, RESOURCES } from "../constants";

export const productsApi = {
    // PUBLIC //

    // QUERY //
    list: (): TApiDefinition<TPageRes<TProductItem>> => ({
        route: `${RESOURCES.PRODUCTS.PUBLIC}/list`,
        method: HttpMethod.GET,
    }),
    getById: (productId: string): TApiDefinition<TProduct> => ({
        route: `${RESOURCES.PRODUCTS.PUBLIC}/${productId}`,
        method: HttpMethod.GET,
        withAuth: true,
        // getById is public, but withAuth that means can include access token with benefits or not
    })
}