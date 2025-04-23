import { logInOrSignUpWithGoogle, logInWithPassword, lookupIdentifier, signUpWithPassword } from "./auth.actions";

// TODO: import lazily

const action = {
    auth: {
        lookupIdentifier,
        logInWithPassword,
        signUpWithPassword,
        logInOrSignUpWithGoogle,
    },
};

export default action;