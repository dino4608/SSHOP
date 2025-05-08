import { TApiResponse } from "@/types/base.types";

export const initialApiResponse: TApiResponse<any> = {
    success: true,
    code: 1,
    error: '',
    data: {} as any,
}

export const BACKEND_URL = process.env.NEXT_PUBLIC_BACKEND_URL;
