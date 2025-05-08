// app/auth/TokenRefresher.tsx
'use client';

import { ACCESS_TOKEN, REFRESH_TOKEN_TTL } from '@/lib/constants';
import clientCookies from '@/lib/utils/clientCookies';
import ms from 'ms';
import { useEffect } from 'react';

const TOKEN_REFRESH_INTERVAL_MIN = REFRESH_TOKEN_TTL - ms('15m');

export function TokenAutoRefresher() {
    useEffect(() => {
        const interval = setInterval(async () => {
            const tokenExp = parseJwtExp(clientCookies.get(ACCESS_TOKEN) || '');
            const now = Math.floor(Date.now() / 1000);

            if (tokenExp && tokenExp - now < 60 * 60) { // còn < 1h
                await fetch('/api/auth/refresh'); // client-side → Spring Boot set cookie
            }
        }, TOKEN_REFRESH_INTERVAL_MIN * 60 * 1000);

        return () => clearInterval(interval);
    }, []);

    return null;
}

function parseJwtExp(token: string): number | null {
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.exp;
    } catch (e) {
        return null;
    }
}
