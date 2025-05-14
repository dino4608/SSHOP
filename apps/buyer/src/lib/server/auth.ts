// lib/utils/auth.ts
import { ACCESS_TOKEN } from "../constants";
import { serverCookies } from "../storage/cookie.server";

export const asyncGetAccessToken = async () => {
    const token = await serverCookies.get(ACCESS_TOKEN);
    return token || null;
}

export const asyncIsAuthenticated = async () => {
    return Boolean(await asyncGetAccessToken());
}