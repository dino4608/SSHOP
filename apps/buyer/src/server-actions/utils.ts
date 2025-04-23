import { TActionState, TApiResponse } from "@/types/base.type";

export const initActionState: TActionState<any> = {
    success: true,
    message: '',
    data: {} as any,
}

export const wrap = async <T>(
    action: () => Promise<TApiResponse<T>>
): Promise<TActionState<T>> => {
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