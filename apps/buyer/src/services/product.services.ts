import { httpClient } from "@/lib/httpclient";
import { Category } from "@/types/product.types";

// CATEGORY_RESOURCE
const CATEGORY_RESOURCE = '/category';

export const getTree = () =>
    httpClient.get<Category[]>({
        endpoint: `${CATEGORY_RESOURCE}/tree`,
        withAuth: false
    });