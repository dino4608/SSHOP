// Tạo hàm get, post, put, delete chuẩn REST

import { httpFetch } from './request';

function buildUrl(endpoint: string, query?: any) {
    if (!query) return endpoint;
    const queryRecord: Record<string, string> = query;
    const q = new URLSearchParams(queryRecord).toString();
    return `${endpoint}?${q}`;
}

type TGetRequestParams<T> = {
    endpoint: string;
    query?: any;
    withAuth?: boolean;
};

type TPostRequestParams<T> = {
    endpoint: string;
    body?: any;
    withAuth?: boolean;
};

export const httpClient = {
    get: <T>({ endpoint, query, withAuth = true }: TGetRequestParams<T>) =>
        httpFetch<T>(buildUrl(endpoint, query), { method: 'GET' }, withAuth),

    post: <T>({ endpoint, body, withAuth }: TPostRequestParams<T>) =>
        httpFetch<T>(endpoint, {
            method: 'POST',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    put: <T>(endpoint: string, body?: any, withAuth = true) =>
        httpFetch<T>(endpoint, {
            method: 'PUT',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    patch: <T>(endpoint: string, body?: any, withAuth = true) =>
        httpFetch<T>(endpoint, {
            method: 'PATCH',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    delete: <T>(endpoint: string, query?: any, withAuth = true) =>
        httpFetch<T>(buildUrl(endpoint, query), { method: 'DELETE' }, withAuth),
};
