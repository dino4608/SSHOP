import { TApiResponse } from "@/types/base.types";
import { TApiDefinition } from "../api-definition/config";
import { BACKEND_URL, HttpMethod } from "../constants";

export function buildEndpoint(domain: string, route: string, query?: any): RequestInfo {
    if (!query) return `${domain}/${route}`;
    const queryRecord: Record<string, string> = query;
    const queryString = new URLSearchParams(queryRecord).toString();
    return `${domain}/${route}?${queryString}`;
}

export function buildOptions(method: HttpMethod, body?: any): RequestInit {
    if (!body) return { method };
    return {
        method,
        body: JSON.stringify(body),
    }
}

export function withAuth(endpoint: string) {
    if (!endpoint || typeof endpoint !== 'string')
        throw new Error('>>> withAuth: invalid endpoint');

    const normalized = endpoint.toLowerCase().trim();

    if (normalized.startsWith('/public/')) return false; // no auth

    return true; // requires auth
}

export const normalizeResponse = async <T>(response: Response) => {
    const json = await response.json() as TApiResponse<T>;

    // TEST
    console.log('>>> json res: ' + json);
    console.log('>>> cookies res: ' + response.headers.get('set-cookie'));

    if (!json.success) {
        console.error(`>>> Api error: ${json.error}`);
    }

    return json;
}

export const normalizeError = <T>(error: any) => {
    console.error(`>>> Api error: ${error.message || 'Lỗi không xác định'}`);

    return {
        success: false,
        code: 0,
        error: 'Lỗi không xác định',
        data: {} as T
    }
}

// NOTE:
// URL: <protocol>://<domain>:<port>/<route>?<query>#<fragment>
// Domain      | Tên miền (gốc)                     | `https://api.example.com`
// Route       | Đường dẫn sau domain               | `/users`, `/products/123/reviews`
// Query       |                                    | `?name=dino`
// Method      |                                    | `GET`, `POST`, `PATCH`, `DELETE`
// Endpoint    | URL cụ thể đại diện cho tài nguyên | `https://api.example.com` + `/users` + `?name=dino`
export const fetchSafely = async <T = any>(
    api: TApiDefinition,
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