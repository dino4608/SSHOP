import { TServerActionResult, TApiResponse } from "@/types/base.types";

export const normalizeResult = async <T>(
    action: () => Promise<TApiResponse<T>>
): Promise<TServerActionResult<T>> => {
    try {
        const { success, error, data } = await action();
        return { success, message: error, data };

    } catch (err: any) {
        console.error(`>>> SERVER ACTION ERROR: ${err.message}`);

        return {
            success: false,
            message: err.message || 'Thành thật rất tiếc, đã có lỗi trên máy chủ!',
            data: {} as T,
        };
    }
};