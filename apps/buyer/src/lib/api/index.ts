import { authApi } from "./auth.api";
import { categoriesApi } from "./categories.api";
import { productsApi } from "./products.api";

export const api = {
    auth: authApi,
    category: categoriesApi,
    products: productsApi
}