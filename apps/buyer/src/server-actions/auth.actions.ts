'use server';

import { api } from "@/services";
import { TLookUpFormData, TLogInFormData } from "@/validations/auth.validations";
import { wrap } from "./utils";

export const lookupIdentifier = async (data: TLookUpFormData) => {
    return await wrap(() => api.auth.lookupIdentifier(data));
};

export const loginWithPassword = async (data: TLogInFormData & TLookUpFormData) => {
    return await wrap(() => api.auth.loginWithPassword(data))
}

export const signupWithPassword = async (data: TLogInFormData & TLookUpFormData) => {
    return await wrap(() => api.auth.signupWithPassword(data))
}

export const loginOrSignupWithGoogle = async (data: any) => {
    return await wrap(() => api.auth.loginOrSignupWithGoogle(data))
}