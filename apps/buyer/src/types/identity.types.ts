/*
 * NOTE:
 * Naming conventions for suffixes
 * - Http: query, body, request
 * - Non-http: result
 * - UI: data
 * - Function: result, output
 */

// Entity //
export type TUser = {
    status: 'LACK_INFO' | 'LIVE' | 'DEACTIVATED' | 'SUSPENDED' | 'DELETED',
    username: string,
    email: string,
    phone: string,
    isEmailVerified: boolean,
    isPhoneVerified: boolean,
    name: string,
    photo: string,
    dob: Date
    gender: 'MALE' | 'FEMALE',
}

// Query //
export type TLookupQuery = {
    email: string;
}

export type TLogInOrSignUpWithGoogleQuery = {
    code: string;
}

// Body //
export type TLogInWithPasswordBody = {
    email: string,
    password: string,
}

// Response //
export type TLookupResponse = {
    isEmailProvided: boolean;
    isPasswordProvided: boolean;
}

export type TAuthResponse = {
    authenticated: boolean;
    accessToken: string;
    user: TUser,
}