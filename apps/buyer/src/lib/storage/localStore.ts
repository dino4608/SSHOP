const isBrowser = typeof window !== 'undefined';

const clientLocalStorage = {
    set: <T = string>(key: string, value: T) => {
        if (!isBrowser) {
            throw new Error(`clientLocalStorage cannot be used outside the browser`);
        }

        const stringified = typeof value === 'string'
            ? value
            : JSON.stringify(value);
        localStorage.setItem(key, stringified);
    },

    get: <T = string>(key: string): T | null => {
        if (!isBrowser) {
            throw new Error(`clientLocalStorage cannot be used outside the browser`);
        }

        const value = localStorage.getItem(key);
        if (value === null) return null;

        try {
            return JSON.parse(value) as T;
        } catch {
            return value as T;
        }
    },

    remove: (key: string) => {
        if (!isBrowser) {
            throw new Error(`clientLocalStorage cannot be used outside the browser`);
        }

        localStorage.removeItem(key);
    },
};

export default clientLocalStorage;
