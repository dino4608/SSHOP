import { loginOrSignupWithGoogle, loginWithPassword, lookupIdentifier, signupWithPassword } from "./auth.actions";

// TODO: import lazily

const serverActions = {
    auth: {
        lookupIdentifier,
        loginWithPassword,
        signupWithPassword,
        loginOrSignupWithGoogle,
    },
};

export default serverActions;