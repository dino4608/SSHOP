import { TAuthResponse, TLoginOrSignUpWithGoogleBody, TLoginWithPasswordBody, TLookupIdentifierQuery, TLookupIdentifierResponse, TUser } from "../../types/auth.types";
import { HttpMethod } from "../constants";
import { TApiDefinition } from "./config";

// PUBLIC_AUTH_RESOURCE //
const PUBLIC_AUTH_RESOURCE = '/public/auth';

// QUERY //
export const lookupIdentifier = (query: TLookupIdentifierQuery): TApiDefinition<TLookupIdentifierResponse> => ({
    route: `${PUBLIC_AUTH_RESOURCE}/lookup`,
    method: HttpMethod.GET,
    query,
});

// COMMAND //
export const loginWithPassword = (body: TLoginWithPasswordBody): TApiDefinition<TAuthResponse> => ({
    route: `${PUBLIC_AUTH_RESOURCE}/login/password`,
    method: HttpMethod.POST,
    body,
});

export const signupWithPassword = (body: TLoginWithPasswordBody): TApiDefinition<TAuthResponse> => ({
    route: `${PUBLIC_AUTH_RESOURCE}/signup/password`,
    method: HttpMethod.POST,
    body,
});

export const loginOrSignupWithGoogle = (body: TLoginOrSignUpWithGoogleBody): TApiDefinition<TAuthResponse> => ({
    route: `${PUBLIC_AUTH_RESOURCE}/oauth2/google`,
    method: HttpMethod.POST,
    body,
});

export const refresh = (): TApiDefinition<TAuthResponse> => ({
    route: `${PUBLIC_AUTH_RESOURCE}/refresh`,
    method: HttpMethod.POST,
});

// PRIVATE_AUTH_RESOURCE //
const PRIVATE_AUTH_RESOURCE = '/auth';

// QUERY //
export const getCurrentUser = (): TApiDefinition<TUser> => ({
    route: `${PRIVATE_AUTH_RESOURCE}/me`,
    method: HttpMethod.GET,
});

// COMMAND //
export const logout = (): TApiDefinition<TAuthResponse> => ({
    route: `${PRIVATE_AUTH_RESOURCE}/logout`,
    method: HttpMethod.POST,
});


