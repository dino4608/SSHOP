// Tạo hàm get, post, put, delete chuẩn REST

import { apiFetch } from './client';

type QueryParams = Record<string, any>;

function buildUrl(url: string, query?: QueryParams) {
    if (!query) return url;
    const q = new URLSearchParams(query).toString();
    return `${url}?${q}`;
}

export const api = {
    get: <T>(url: string, query?: QueryParams, withAuth = true) =>
        apiFetch<T>(buildUrl(url, query), { method: 'GET' }, withAuth),

    post: <T>(url: string, body?: any, withAuth = true) =>
        apiFetch<T>(url, {
            method: 'POST',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    put: <T>(url: string, body?: any, withAuth = true) =>
        apiFetch<T>(url, {
            method: 'PUT',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    patch: <T>(url: string, body?: any, withAuth = true) =>
        apiFetch<T>(url, {
            method: 'PATCH',
            body: body ? JSON.stringify(body) : undefined,
        }, withAuth),

    delete: <T>(url: string, query?: QueryParams, withAuth = true) =>
        apiFetch<T>(buildUrl(url, query), { method: 'DELETE' }, withAuth),
};
