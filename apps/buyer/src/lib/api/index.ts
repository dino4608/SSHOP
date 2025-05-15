import { getCurrentUser, loginOrSignupWithGoogle, loginWithPassword, logout, lookupIdentifier, refresh, signupWithPassword } from "./auth.api";
import { getTree } from "./categories.api";
import { productsApi } from "./products.api";

export const api = {
    auth: {
        lookupIdentifier,
        loginWithPassword,
        signupWithPassword,
        loginOrSignupWithGoogle,
        refresh,
        getCurrentUser,
        logout,
    },
    category: {
        getTree
    },
    products: productsApi
}