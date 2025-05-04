import { TServerActionResult } from "@/types/base.types";
import { loginOrSignupWithGoogle, loginWithPassword, lookupIdentifier, signupWithPassword } from "./auth.actions";

// TODO: import lazily

export const initialServerActionError: TServerActionResult<any> = {
    success: true,
    message: '',
    data: {} as any,
}

export const serverActions = {
    auth: {
        lookupIdentifier,
        loginWithPassword,
        signupWithPassword,
        loginOrSignupWithGoogle,
    },
};