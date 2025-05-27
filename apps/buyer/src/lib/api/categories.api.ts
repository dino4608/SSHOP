import { TCategory } from "@/types/category.types";
import { HttpMethod, RESOURCES } from "../constants";
import { TApiDefinition } from "@/types/base.types";

export const categoriesApi = {
    // PUBLIC //

    // QUERY //
    getTree: (): TApiDefinition<TCategory[]> => ({
        route: `${RESOURCES.CATEGORY.PUBLIC}/tree`,
        method: HttpMethod.GET,
    }),
}