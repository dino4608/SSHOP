'use server';

import { api } from "@/services";
import { TLoginOrSignUpWithGoogleBody, TLoginWithPasswordBody, TLookupIdentifierQuery } from "@/types/identity.types";

export const lookupIdentifier = async (data: TLookupIdentifierQuery) => {
    return await api.auth.lookupIdentifier(data);
};

export const loginWithPassword = async (data: TLoginWithPasswordBody) => {
    return await api.auth.loginWithPassword(data);
}

export const signupWithPassword = async (data: TLoginWithPasswordBody) => {
    return await api.auth.signupWithPassword(data)
}

export const loginOrSignupWithGoogle = async (data: TLoginOrSignUpWithGoogleBody) => {
    return await api.auth.loginOrSignupWithGoogle(data)
}