import { TAddress } from "@/types/address.types";
import { TApiDefinition } from "@/types/base.types";
import { HttpMethod, RESOURCES } from "../constants";

export const addressesApi = {
    // BUYER PRIVATE //

    // QUERY //
    getDefault: (): TApiDefinition<TAddress> => ({
        route: `${RESOURCES.ADDRESSES.PRIVATE}/default`,
        method: HttpMethod.GET,
    }),

};