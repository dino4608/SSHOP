'use server';

import { api } from "@/services";
import { TLookUpFormData, TLogInFormData } from "@/validations/auth.validations";
import { wrap } from "./utils";
import { TLogInOrSignUpWithGoogleQuery } from "@/types/identity.types";

export const lookupIdentifier = async (data: TLookUpFormData) => {
    return await wrap(() => api.auth.lookupIdentifier(data));
};

export const logInWithPassword = async (data: TLogInFormData & TLookUpFormData) => {
    return await wrap(() => api.auth.logInWithPassword(data))
}

export const signUpWithPassword = async (data: TLogInFormData & TLookUpFormData) => {
    return await wrap(() => api.auth.signUpWithPassword(data))
}

export const logInOrSignUpWithGoogle = async (data: any) => {
    return await wrap(() => api.auth.logInOrSignUpWithGoogle(data))
}