import { apiClient } from "@/lib/api";
import { TAuthResponse, TLogInOrSignUpWithGoogleQuery, TLogInWithPasswordBody, TLookupQuery, TLookupResponse } from "../types/identity.types";

// AUTH_RESOURCE //
const AUTH_RESOURCE = '/auth';

export const lookupIdentifier = async (query: TLookupQuery) => await apiClient.get<TLookupResponse>({
    endpoint: `${AUTH_RESOURCE}/lookup`,
    query,
    withAuth: false,
});

export const logInWithPassword = async (body: TLogInWithPasswordBody) => await apiClient.post<TAuthResponse>({
    endpoint: `${AUTH_RESOURCE}/login/password`,
    body,
    withAuth: false,
});

export const signUpWithPassword = async (body: TLogInWithPasswordBody) => await apiClient.post<TAuthResponse>({
    endpoint: `${AUTH_RESOURCE}/signup/password`,
    body,
    withAuth: false,
});

export const logInOrSignUpWithGoogle = async (body: TLogInOrSignUpWithGoogleQuery) => await apiClient.post<TAuthResponse>({
    endpoint: `${AUTH_RESOURCE}/oauth2/google`,
    body,
    withAuth: false,
});

