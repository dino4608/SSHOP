// lib/httpClient/index.ts: Tạo hàm get, post, put, delete chuẩn REST: là lớp API tiện dụng

import { httpRequest } from './request';

type TGetRequestParams<T> = {
    endpoint: string;
    query?: T;
    withAuth?: boolean;
};

type TPostRequestParams<T> = {
    endpoint: string;
    body?: T;
    withAuth?: boolean;
};

function buildUrl(endpoint: string, query?: any) {
    if (!query) return endpoint;
    const queryRecord: Record<string, string> = query;
    const q = new URLSearchParams(queryRecord).toString();
    return `${endpoint}?${q}`;
}

export const httpClient = {
    get: <T>({ endpoint, query, withAuth = true }: TGetRequestParams<T>) =>
        httpRequest<T>(buildUrl(endpoint, query), {
            method: 'GET'
        }, withAuth),

    post: <T>({ endpoint, body, withAuth }: TPostRequestParams<T>) =>
        httpRequest<T>(endpoint, {
            method: 'POST',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    put: <T>(endpoint: string, body?: any, withAuth = true) =>
        httpRequest<T>(endpoint, {
            method: 'PUT',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    patch: <T>(endpoint: string, body?: any, withAuth = true) =>
        httpRequest<T>(endpoint, {
            method: 'PATCH',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    delete: <T>(endpoint: string, query?: any, withAuth = true) =>
        httpRequest<T>(buildUrl(endpoint, query), {
            method: 'DELETE'
        }, withAuth),
};
