import { TApiResponse } from "@/types/base.types";
import { TApiDefinition } from "../api/config";
import { BACKEND_URL, HttpMethod } from "../constants";

export function buildEndpoint(domain: string, route: string, query?: any): RequestInfo {
    console.log(`>>> buildEndpoint: endpoint: ${domain + route}`);

    if (!query) return `${domain + route}`;
    const queryRecord: Record<string, string> = query;
    const queryString = new URLSearchParams(queryRecord).toString();
    return `${domain + route}?${queryString}`;
}

export function buildOptions(method: HttpMethod, body?: any): RequestInit {
    if (!body) return { method };
    return {
        method,
        body: JSON.stringify(body),
    }
}

export function withAuth(route: string) {
    if (!route || typeof route !== 'string')
        throw new Error('>>> withAuth: invalid endpoint');

    const normalized = route.toLowerCase().trim();

    if (normalized.startsWith('/public')) return false; // no auth

    return true; // requires auth
}

export const normalizeResponse = async <T>(response: Response) => {
    const json = await response.json() as TApiResponse<T>;

    if (!json.success) {
        console.warn(`>>> normalizeResponse: fetch error: ${json.error}`);
    }

    return json;
}

export const normalizeError = <T>(error: any) => {
    console.error(`>>> normalizeError: fetch error: ${error.message || 'Lỗi không xác định'}`);

    return {
        success: false,
        status: 500,
        code: 0,
        error: 'Lỗi không xác định',
        data: {} as T
    }
}

// NOTE: URL
// URL: <protocol>://<domain>:<port>/<route>?<query>#<fragment>
// Domain      | Tên miền (gốc)                     | `https://api.example.com`
// Route       | Đường dẫn sau domain               | `/users`, `/products/[id]/reviews`
// Path        | Giá trị cụ thể của route           | `/product/123`
// Query       |                                    | `?name=dino`
// Method      |                                    | `GET`, `POST`, `PATCH`, `DELETE`
// Endpoint    | URL cụ thể đại diện cho tài nguyên | `https://api.example.com` + `/users` + `?name=dino`
export const fetchSafely = async <T = any>(
    api: TApiDefinition<T>,
    fetchCore: (endpoint: RequestInfo, options?: RequestInit, withAuth?: boolean) => Promise<Response>
): Promise<TApiResponse<T>> => {

    const domain = BACKEND_URL
    const { route, method, query, body } = api

    try {
        const response = await fetchCore(
            buildEndpoint(domain, route, query),
            buildOptions(method, body),
            withAuth(route),
        );

        return normalizeResponse<T>(response)

    } catch (error: any) {
        return normalizeError<T>(error)
    }
}