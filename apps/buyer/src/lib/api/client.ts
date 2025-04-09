// Base apiFetch() – xử lý token, retry

import { API_BASE_URL } from "./config";
import { clearTokens, getAccessToken, getRefreshToken, setAccessToken } from "./token";


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

export async function apiFetch<T = any>(
    endpoint: string,
    options: RequestInit = {},
    withAuth: boolean = true
): Promise<T> {
    // const headers: HeadersInit = {
    //     'Content-Type': 'application/json',
    //     ...(options.headers || {}),
    // };
    const headers: Record<string, string> = {
        'Content-Type': 'application/json',
        ...(options.headers as Record<string, string>),
    };


    if (withAuth) {
        const token = getAccessToken();
        if (token) headers['Authorization'] = `Bearer ${token}`;
    }

    let res = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: headers as HeadersInit,
    });

    if (res.status === 401 && withAuth) {
        const refreshed = await refreshToken();
        if (refreshed) {
            const retryToken = getAccessToken();
            headers['Authorization'] = `Bearer ${retryToken}`;
            res = await fetch(`${API_BASE_URL}${endpoint}`, {
                ...options,
                headers,
            });
        } else {
            clearTokens();
            window.location.href = '/'; // hoặc router.push('/')
            throw new Error('Phiên đăng nhập đã hết, vui lòng đăng nhập lại');
        }
    }

    const json = await res.json();

    if (!json.success) {
        throw new Error(json.message || 'Lỗi không xác định');
    }

    return json.data as T;
}
