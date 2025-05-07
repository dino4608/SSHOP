// Base apiFetch() – xử lý token, retry

import { TApiResponse } from "@/types/base.types";
import { clearTokens, getAccessToken, getRefreshToken, setAccessToken } from "./token";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

let isRefreshing = false;

async function refreshToken(): Promise<boolean> {
    if (isRefreshing) return false;
    isRefreshing = true;

    const refreshToken = getRefreshToken();
    if (!refreshToken) return false;

    try {
        const res = await fetch(`${API_BASE_URL}/auth/refresh`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({ refresh_token: refreshToken }),
        });

        const json = await res.json();
        if (json.success && json.data?.access_token) {
            setAccessToken(json.data.access_token);
            return true;
        }
    } catch (err) {
        console.error('[Refresh Failed]', err);
    } finally {
        isRefreshing = false;
    }

    return false;
}

export async function httpFetch<T = any>(
    endpoint: string,
    options: RequestInit = {},
    withAuth: boolean = true
): Promise<TApiResponse<T>> {

    const headers: Record<string, string> = {
        'Content-Type': 'application/json',
        ...(options.headers as Record<string, string>),
    };

    if (withAuth) {
        const token = getAccessToken();
        if (token) headers['Authorization'] = `Bearer ${token}`;
    }

    try {
        let fetchRes = await fetch(`${API_BASE_URL}${endpoint}`, {
            ...options,
            credentials: 'include',
            headers: headers as HeadersInit,
        });

        if (fetchRes.status === 401 && withAuth) {
            const refreshed = await refreshToken();
            if (refreshed) {
                const retryToken = getAccessToken();
                headers['Authorization'] = `Bearer ${retryToken}`;
                fetchRes = await fetch(`${API_BASE_URL}${endpoint}`, {
                    ...options,
                    credentials: 'include',
                    headers,
                });
            } else {
                clearTokens();
                window.location.href = '/'; // hoặc router.push('/')
                throw new Error('Phiên đăng nhập đã hết, vui lòng đăng nhập lại');
            }
        }

        const jsonRes = await fetchRes.json() as TApiResponse<T>;
        console.log('>>> json res: ' + jsonRes);
        console.log('>>> cookies res: ' + fetchRes.headers.get('set-cookie'));



        if (!jsonRes.success) {
            console.error(`>>> Http client error: ${jsonRes.error}`);
        }

        return jsonRes;

    } catch (error: any) {
        console.error(`>>> Http client error: ${error.message || 'Lỗi không xác định'}`);

        return {
            success: false,
            code: 0,
            error: 'Lỗi không xác định',
            data: {} as T
        }
    }
}
