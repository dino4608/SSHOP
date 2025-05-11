import { getCurrentUser, loginOrSignupWithGoogle, loginWithPassword, logout, lookupIdentifier, refresh, signupWithPassword } from "./identity.api";
import { getTree } from "./product.api";

export const api = {
    category: {
        getTree,
    },
    auth: {
        lookupIdentifier,
        loginWithPassword,
        signupWithPassword,
        loginOrSignupWithGoogle,
        refresh,
        getCurrentUser,
        logout,
    },
}