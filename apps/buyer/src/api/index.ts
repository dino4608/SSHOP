import { loginOrSignupWithGoogle, loginWithPassword, lookupIdentifier, signupWithPassword } from "./identity.services";
import { getTree } from "./product.services";

export const api = {
    category: {
        getTree,
    },
    auth: {
        lookupIdentifier,
        loginWithPassword,
        signupWithPassword,
        loginOrSignupWithGoogle,
    },
}