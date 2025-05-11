// app/auth/TokenRefresher.tsx
'use client';

import { api } from '@/api';
import { ACCESS_TOKEN } from '@/lib/constants';
import clientCookies from '@/lib/utils/clientCookies';
import ms from 'ms';
import { useEffect } from 'react';

const TOKEN_REFRESH_THRESHOLD = '10m';
const TOKEN_REFRESH_INTERVAL = '9m';

export function TokenAutoRefresher() {
    useEffect(() => {
        // NOTE:
        // setInterval, clearInterval: a pair of global function in JavaScript
        // setInterval will returns ID interval
        // ID interval will be used to clearInterval when component unmount.
        const intervalId = setInterval(async () => {
            // compute by Epoch timestamp, Unix timestamp (integer in seconds)
            const tokenExpiry = parseJwtExp(clientCookies.get(ACCESS_TOKEN) || '');
            if (!tokenExpiry) throw new Error(`>>> TokenAutoRefresher: tokenExpiry should not be NULL`);

            const now = Math.floor(Date.now() / 1000);


            if (tokenExpiry - now < ms(TOKEN_REFRESH_THRESHOLD) / 1000) {
                // call api route
                // client-side → Spring Boot set cookie
                // await fetch('/api/auth/refresh');

                // call direct api
                const apiRes = await api.auth.refresh();
                console.log(`>>> TokenAutoRefresher: apiRes: ${apiRes}`);

            }
        }, ms(TOKEN_REFRESH_INTERVAL));

        return () => clearInterval(intervalId);
    }, []);

    return null;
}

function parseJwtExp(token: string): number | null {
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));

        console.log(`>>> parseJwtExp: payload: ${payload}`);

        return payload.exp; // Epoch timestamp, Unix timestamp (integer in seconds)

    } catch (e) {
        throw new Error('>>> parseJwtExp: failed');
    }
}
