import { TApiResponse } from "@/types/base.types";

export const initialApiResponse: TApiResponse<any> = {
    success: true,
    code: 1,
    error: '',
    data: {} as any,
}

export * from "./env";

export * from "./string";

export enum HttpMethod {
    GET = 'GET',
    POST = 'POST',
    PATCH = 'PATCH',
    PUT = 'PUT',
    DELETE = 'DELETE',
}
