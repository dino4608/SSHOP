// Chứa API_BASE_URL và cấu hình khác

export const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || '';

export const API_STATUS = {
    SUCCESS: true,
    UNAUTHORIZED: 401,
};
