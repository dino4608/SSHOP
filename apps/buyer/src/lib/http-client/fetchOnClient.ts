// clientFetch.ts
export async function clientFetch(input: RequestInfo, init?: RequestInit) {
    return fetch(input, {
        ...init,
        credentials: "include", // để gửi cookie (session, access token)
    });
}


// serverFetch.ts (Next.js App Router)
import { TApiResponse } from "@/types/base.types";
import { BACKEND_URL } from "../constants";
import clientCookies from "../utils/clientCookies";


export async function fetchOnClient<T = any>(
    endpoint: RequestInfo,
    options: RequestInit = {},
    withAuth: boolean = true
): Promise<TApiResponse<T>> {

    // config header
    const headers: Record<string, string> = {
        'Content-Type': 'application/json',
        ...(options.headers as Record<string, string>),
    };

    // include ACCESS_TOKEN
    if (withAuth) {
        const token = clientCookies.get("ACCESS_TOKEN");
        headers['Authorization'] = `Bearer ${token}`;
    }

    // fetch on Next server
    try {
        const fetchRes = await fetch(`${BACKEND_URL}${endpoint}`, {
            ...options,
            credentials: "include",
            headers: headers as HeadersInit
        });

        // TODO #1 refresh token

        const jsonRes = await fetchRes.json() as TApiResponse<T>;
        console.log('>>> json res: ' + jsonRes);
        console.log('>>> cookies res: ' + fetchRes.headers.get('set-cookie'));

        if (!jsonRes.success) {
            console.error(`>>> Api error: ${jsonRes.error}`);
        }

        return jsonRes;

    } catch (error: any) {
        console.error(`>>> Api error: ${error.message || 'Lỗi không xác định'}`);

        return {
            success: false,
            code: 0,
            error: 'Lỗi không xác định',
            data: {} as T
        }
    }
}
