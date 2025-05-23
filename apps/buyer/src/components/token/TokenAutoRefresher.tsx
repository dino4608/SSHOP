// app/auth/TokenRefresher.tsx
'use client';
import { useIsAuthenticated } from '@/hooks/useIsAuthenticated';
import { api } from '@/lib/api';
import { ACCESS_TOKEN } from '@/lib/constants';
import { clientFetch } from '@/lib/fetch/fetch.client';
import clientCookies from '@/lib/storage/cookie.client';
import { parseJwtExp } from '@/lib/utils';
import ms from 'ms';
import { useEffect } from 'react';

const TOKEN_REFRESH_THRESHOLD = '10m';
const TOKEN_REFRESH_INTERVAL = '9m';

export const TokenAutoRefresher = () => {
    const isAuthenticated = useIsAuthenticated();

    useEffect(() => {
        if (!isAuthenticated) return;

        // NOTE: setInterval, clearInterval
        // setInterval, clearInterval: a pair of global function in JavaScript
        // setInterval will returns ID interval
        // ID interval will be used to clearInterval when component unmount.
        const intervalId = setInterval(async () => {
            // compute by Epoch / Unix timestamp (number in seconds)
            const tokenExpiry = parseJwtExp(clientCookies.get(ACCESS_TOKEN) || '');
            if (!tokenExpiry) throw new Error(`>>> TokenAutoRefresher: tokenExpiry should not be NULL`);

            const now = Math.floor(Date.now() / 1000);

            if (tokenExpiry - now < ms(TOKEN_REFRESH_THRESHOLD) / 1000) {
                const apiRes = await clientFetch(api.auth.refresh());
                console.log(`>>> TokenAutoRefresher: apiRes: ${apiRes}`);
            }
        }, ms(TOKEN_REFRESH_INTERVAL));

        return () => clearInterval(intervalId);
    }, [isAuthenticated]);

    return null;
}