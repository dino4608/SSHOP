import { httpClient } from "@/lib/http-client";
import { Category } from "@/types/product.types";

// CATEGORY_RESOURCE
const CATEGORY_RESOURCE = '/category';

export const getTree = () =>
    httpClient.get<Category[]>({
        endpoint: `${CATEGORY_RESOURCE}/tree`,
        withAuth: false
    });