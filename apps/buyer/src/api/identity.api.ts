import { httpClient } from "@/lib/http-client";
import { TAuthResponse, TLoginOrSignUpWithGoogleBody, TLoginWithPasswordBody, TLookupIdentifierQuery, TLookupIdentifierResponse } from "../types/identity.types";

// AUTH_RESOURCE //
const AUTH_RESOURCE = '/auth';

export const lookupIdentifier = async (query: TLookupIdentifierQuery) =>
    await httpClient.get<TLookupIdentifierResponse>({
        endpoint: `${AUTH_RESOURCE}/lookup`,
        query,
        withAuth: false,
    });

export const loginWithPassword = async (body: TLoginWithPasswordBody) =>
    await httpClient.post<TAuthResponse>({
        endpoint: `${AUTH_RESOURCE}/login/password`,
        body,
        withAuth: false,
    });

export const signupWithPassword = async (body: TLoginWithPasswordBody) =>
    await httpClient.post<TAuthResponse>({
        endpoint: `${AUTH_RESOURCE}/signup/password`,
        body,
        withAuth: false,
    });

export const loginOrSignupWithGoogle = async (body: TLoginOrSignUpWithGoogleBody) =>
    await httpClient.post<TAuthResponse>({
        endpoint: `${AUTH_RESOURCE}/oauth2/google`,
        body,
        withAuth: false,
    });

