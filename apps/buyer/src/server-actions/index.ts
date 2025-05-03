import { loginOrSignupWithGoogle, loginWithPassword, lookupIdentifier, signupWithPassword } from "./auth.actions";

// TODO: import lazily

export const serverActions = {
    auth: {
        lookupIdentifier,
        loginWithPassword,
        signupWithPassword,
        loginOrSignupWithGoogle,
    },
};