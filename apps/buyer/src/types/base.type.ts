export type TApiResponse<T> = {
    success: boolean;
    code: number;
    error: string;
    data: T;
}

export type TActionState<T> = {
    success: boolean;
    message: string;
    data: T
}