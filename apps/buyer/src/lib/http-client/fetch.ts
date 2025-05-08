// fetchSafely.ts (Next.js App Router)

// EXP:
// Gửi request
// Gắn token/cookie
// Sentry error
// Parse JSON

import { TApiResponse } from "@/types/base.types";
import { BACKEND_URL } from "../constants";
import clientCookies from "../utils/clientCookies";
import { isBrowser } from "../utils";
import { cookies } from "next/headers";

const buildHeader = async (
    options: RequestInit = {},
    withAuth: boolean = true
): Promise<HeadersInit> => {
    // init header
    const headers: Record<string, string> = {
        'Content-Type': 'application/json',
        ...(options.headers as Record<string, string>),
    };

    if (isBrowser) {
        // get client cookies
        const ACCESS_TOKEN = clientCookies.get("ACCESS_TOKEN");

        // include ACCESS_TOKEN
        if (withAuth && ACCESS_TOKEN) {
            headers['Authorization'] = `Bearer ${ACCESS_TOKEN}`
        }

        return headers;
    } else {
        // get server cookies
        const cookieStore = await cookies();
        const REFRESH_TOKEN = cookieStore.get("REFRESH_TOKEN")?.value;
        const ACCESS_TOKEN = cookieStore.get("ACCESS_TOKEN")?.value;

        // include ACCESS_TOKEN
        if (withAuth && ACCESS_TOKEN) {
            headers['Authorization'] = `Bearer ${ACCESS_TOKEN}`;
        }

        // include REFRESH_TOKEN
        // NOTE: 'Cookie'
        // Next tự inject cookie từ next/headers() khi bạn fetch từ RRC
        // Đính cookie thủ công nếu dùng Api route h hay Server action
        if (REFRESH_TOKEN) {
            headers['Cookie'] = `REFRESH_TOKEN=${REFRESH_TOKEN}`;

            // TEST
            console.log('>>> cookies toString: ', cookieStore.toString());
            console.log('>>> cookies getValue: ', REFRESH_TOKEN);
        }
    }

    return headers;
}

const normalizeFetchRes = async <T>(response: Response) => {
    const json = await response.json() as TApiResponse<T>;

    // TEST
    console.log('>>> json res: ' + json);
    console.log('>>> cookies res: ' + response.headers.get('set-cookie'));

    if (!json.success) {
        console.error(`>>> Api error: ${json.error}`);
    }

    return json;
}

const normalizeFetchError = <T>(error: any) => {
    console.error(`>>> Api error: ${error.message || 'Lỗi không xác định'}`);

    return {
        success: false,
        code: 0,
        error: 'Lỗi không xác định',
        data: {} as T
    }
}

export async function fetchSafely<T = any>(
    endpoint: RequestInfo,
    options: RequestInit = {},
    withAuth: boolean = true
): Promise<TApiResponse<T>> {

    // config header
    const headers = await buildHeader(options, withAuth);

    // fetch on Next server
    try {
        const response = await fetch(`${BACKEND_URL}${endpoint}`, {
            ...options,
            ...(isBrowser ? {} : { credentials: "include" }),  // để browser gửi cookie (refresh token)
            headers,
        });

        // TODO #1 refresh token

        return normalizeFetchRes<T>(response)

    } catch (error: any) {
        return normalizeFetchError<T>(error)
    }
}

