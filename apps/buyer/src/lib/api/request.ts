// Base apiFetch() – xử lý token, retry

import { TApiResponse } from "@/types/base.type";
import { clearTokens, getAccessToken, getRefreshToken, setAccessToken } from "./token";

const API_BASE_URL = process.env.API_BASE_URL;

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
        let fetchResponse = await fetch(`${API_BASE_URL}${endpoint}`, {
            ...options,
            headers: headers as HeadersInit,
        });

        if (fetchResponse.status === 401 && withAuth) {
            const refreshed = await refreshToken();
            if (refreshed) {
                const retryToken = getAccessToken();
                headers['Authorization'] = `Bearer ${retryToken}`;
                fetchResponse = await fetch(`${API_BASE_URL}${endpoint}`, {
                    ...options,
                    headers,
                });
            } else {
                clearTokens();
                window.location.href = '/'; // hoặc router.push('/')
                throw new Error('Phiên đăng nhập đã hết, vui lòng đăng nhập lại');
            }
        }

        // Chuyển đổi phản hồi thành JSON
        const apiResponse: TApiResponse<T> = await fetchResponse.json();

        if (!apiResponse.success) {
            throw new Error(apiResponse.error || 'Lỗi không xác định');
        }

        return apiResponse;
    } catch (error: any) {
        // Bắt và xử lý lỗi: Lỗi mã trạng thái, lỗi mạng, hoặc lỗi khi chuyển đổi JSON
        console.error('Error fetching API:', error.message);
        throw error;  // Ném lại lỗi để caller có thể xử lý tiếp
    }
}
