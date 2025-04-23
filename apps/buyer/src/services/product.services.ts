import { apiClient } from "@/lib/api";
import { Category } from "@/types/product.types";

// CATEGORY_RESOURCE
const CATEGORY_RESOURCE = '/category';

export const getTree = () => apiClient.get<Category[]>({
    endpoint: `${CATEGORY_RESOURCE}/tree`,
    withAuth: false
});