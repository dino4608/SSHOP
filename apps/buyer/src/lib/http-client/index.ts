// lib/httpClient/index.ts: Tạo hàm get, post, put, delete chuẩn REST: là lớp API tiện dụng

import { httpRequest } from './request';

type TGetRequestParams = {
    endpoint: string;
    query?: any;
    withAuth?: boolean;
};

type TPostRequestParams = {
    endpoint: string;
    body?: any;
    withAuth?: boolean;
};

type TPutRequestParams = {
    endpoint: string;
    body?: any;
    withAuth?: boolean;
};

type TPatchRequestParams = {
    endpoint: string;
    body?: any;
    withAuth?: boolean;
};

type TDeleteRequestParams = {
    endpoint: string;
    query?: any;
    withAuth?: boolean;
};

function buildUrl(endpoint: string, query?: any) {
    if (!query) return endpoint;
    const queryRecord: Record<string, string> = query;
    const q = new URLSearchParams(queryRecord).toString();
    return `${endpoint}?${q}`;
}

function buildBody(body?: any) {
    if (!body) return undefined;
    return JSON.stringify(body);
}

export const httpClient = {
    get: <T>({ endpoint, query, withAuth = true }: TGetRequestParams) =>
        httpRequest<T>(buildUrl(endpoint, query), {
            method: 'GET'
        }, withAuth),

    post: <T>({ endpoint, body, withAuth }: TPostRequestParams) =>
        httpRequest<T>(endpoint, {
            method: 'POST',
            body: buildBody(body),
        }, withAuth),

    put: <T>({ endpoint, body, withAuth = true }: TPutRequestParams) =>
        httpRequest<T>(endpoint, {
            method: 'PUT',
            body: buildBody(body),
        }, withAuth),

    patch: <T>({ endpoint, body, withAuth = true }: TPatchRequestParams) =>
        httpRequest<T>(endpoint, {
            method: 'PATCH',
            body: buildBody(body),
        }, withAuth),

    delete: <T>({ endpoint, query, withAuth = true }: TDeleteRequestParams) =>
        httpRequest<T>(buildUrl(endpoint, query), {
            method: 'DELETE'
        }, withAuth),
};
