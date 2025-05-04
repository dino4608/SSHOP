'use server';

import { api } from "@/services";
import { TLoginOrSignUpWithGoogleBody, TLoginWithPasswordBody, TLookupIdentifierQuery } from "@/types/identity.types";
import { normalizeResult } from "./config";

export const lookupIdentifier = async (data: TLookupIdentifierQuery) => {
    return await normalizeResult(() => api.auth.lookupIdentifier(data));
};

export const loginWithPassword = async (data: TLoginWithPasswordBody) => {
    return await normalizeResult(() => api.auth.loginWithPassword(data))
}

export const signupWithPassword = async (data: TLoginWithPasswordBody) => {
    return await normalizeResult(() => api.auth.signupWithPassword(data))
}

export const loginOrSignupWithGoogle = async (data: TLoginOrSignUpWithGoogleBody) => {
    return await normalizeResult(() => api.auth.loginOrSignupWithGoogle(data))
}