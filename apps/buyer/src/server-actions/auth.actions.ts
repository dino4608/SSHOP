'use server';
import { api } from "@/lib/api";
import { serverFetch } from "@/lib/fetch/fetch.server";
import { TLoginOrSignUpWithGoogleBody, TLoginWithPasswordBody, TLookupIdentifierQuery } from "@/types/auth.types";

export const lookupIdentifier = async (data: TLookupIdentifierQuery) => {
    return await serverFetch(api.auth.lookupIdentifier(data))
};

export const loginWithPassword = async (data: TLoginWithPasswordBody) => {
    return await serverFetch(api.auth.loginWithPassword(data))
}

export const signupWithPassword = async (data: TLoginWithPasswordBody) => {
    return await serverFetch(api.auth.signupWithPassword(data))
}

export const loginOrSignupWithGoogle = async (data: TLoginOrSignUpWithGoogleBody) => {
    return await serverFetch(api.auth.loginOrSignupWithGoogle(data))
}