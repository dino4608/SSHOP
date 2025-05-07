const isBrowser = typeof window !== 'undefined';

const clientCookies = {
    set: (key: string, value: string, options: { days?: number; path?: string } = {}) => {
        if (!isBrowser) return;
        const { days = 7, path = '/' } = options;
        const expires = new Date();
        expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
        document.cookie = `${key}=${encodeURIComponent(value)}; expires=${expires.toUTCString()}; path=${path}`;
    },

    get: (key: string): string | null => {
        if (!isBrowser) return null;
        const nameEQ = `${key}=`;
        const ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) return decodeURIComponent(c.substring(nameEQ.length));
        }
        return null;
    },

    remove: (key: string, path: string = '/') => {
        if (!isBrowser) return;
        document.cookie = `${key}=; Max-Age=0; path=${path}`;
    },
};

export default clientCookies;