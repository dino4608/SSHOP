import { loginOrSignupWithGoogle, loginWithPassword, lookupIdentifier, signupWithPassword } from "./auth.actions";

// TODO: import lazily

export const server = {
    auth: {
        lookupIdentifier,
        loginWithPassword,
        signupWithPassword,
        loginOrSignupWithGoogle,
    },
};