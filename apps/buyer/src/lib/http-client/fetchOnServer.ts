// serverFetch.ts (Next.js App Router)
import { TApiResponse } from "@/types/base.types";
import { cookies } from "next/headers";
import { BACKEND_URL } from "../constants";


export async function fetchOnServer<T = any>(
    endpoint: RequestInfo,
    options: RequestInit = {},
    withAuth: boolean = true
): Promise<TApiResponse<T>> {

    const cookieStore = await cookies();

    console.log('>>> cookies toString: ', cookieStore.toString());
    console.log('>>> cookies getValue: ', cookieStore.toString());

    // config header
    const headers: Record<string, string> = {
        'Content-Type': 'application/json',
        'Cookie': cookieStore.get("REFRESH_TOKEN")?.value || '',
        ...(options.headers as Record<string, string>),
        // NOTE: 'Cookie': Next tự inject cookie từ next/headers() khi bạn fetch từ Server Component
    };

    // include ACCESS_TOKEN
    if (withAuth) {
        const token = cookieStore.get("ACCESS_TOKEN")?.value;
        if (!token) headers['Authorization'] = `Bearer ${token}`;
    }

    // fetch on Next server
    try {
        const fetchRes = await fetch(`${BACKEND_URL}${endpoint}`, {
            ...options,
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
