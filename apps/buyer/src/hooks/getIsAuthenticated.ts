import { ACCESS_TOKEN } from "../lib/constants";
import { serverCookies } from "../lib/storage/cookie.server";

const getAccessToken = async () => {
    const token = await serverCookies.get(ACCESS_TOKEN);
    return token || null;
}

export const getIsAuthenticated = async () => {
    return Boolean(await getAccessToken());
}