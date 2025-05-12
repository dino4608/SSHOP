import { TCategory } from "@/types/product.types";
import { HttpMethod } from "../constants";
import { TApiDefinition } from "./config";

// PUBLIC_CATEGORY_RESOURCE
const PUBLIC_CATEGORY_RESOURCE = '/public/category';

export const getTree = (): TApiDefinition<TCategory[]> => ({
    route: `${PUBLIC_CATEGORY_RESOURCE}/tree`,
    method: HttpMethod.GET,
});
