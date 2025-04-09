// Lấy/ghi access token & refresh token

export const getAccessToken = () => {
    return typeof window !== 'undefined'
        ? localStorage.getItem('access_token')
        : null;
};

export const getRefreshToken = () => {
    return typeof window !== 'undefined'
        ? localStorage.getItem('refresh_token')
        : null;
};

export const setAccessToken = (token: string) => {
    localStorage.setItem('access_token', token);
};

export const clearTokens = () => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
};
