import { TPageRes } from "@/types/base.types";
import { TProduct, TProductItem } from "@/types/product.types";
import { TApiDefinition } from "./config";
import { HttpMethod } from "../constants";

const PUBLIC_PRODUCT_RESOURCE = '/public/products';

export const productsApi = {

    // PUBLIC //

    // QUERY //

    list: (): TApiDefinition<TPageRes<TProductItem>> => ({
        route: `${PUBLIC_PRODUCT_RESOURCE}/list`,
        method: HttpMethod.GET,
    }),
    getById: (productId: string): TApiDefinition<TProduct> => ({
        route: `${PUBLIC_PRODUCT_RESOURCE}/${productId}`,
        method: HttpMethod.GET,
    })
}