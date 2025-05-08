// lib/api/fetchApi.ts

import { TApiResponse } from "@/types/base.types";
import { fetchOnClient } from "./fetchOnClient";
import { fetchOnServer } from "./fetchOnServer";

export async function fetchApi<T = any>(
    endpoint: RequestInfo,
    options: RequestInit = {},
    withAuth: boolean = true
): Promise<TApiResponse<T>> {

    const isBrowser = typeof window !== "undefined";

    return isBrowser
        ? fetchOnClient(endpoint, options, withAuth)   // Nếu đang chạy trong browser → client fetch
        : fetchOnServer(endpoint, options, withAuth);  // Nếu đang chạy trên server (server action, api route, RSC)
}
