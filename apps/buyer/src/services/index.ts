import { logInOrSignUpWithGoogle, logInWithPassword, lookupIdentifier, signUpWithPassword } from "./identity.services";
import { getTree } from "./product.services";

export const api = {
    category: {
        getTree,
    },
    auth: {
        lookupIdentifier,
        logInWithPassword,
        signUpWithPassword,
        logInOrSignUpWithGoogle,
    },
}