// app/components/auth/TokenRestorer.tsx
'use client';
import { useEffect, useState } from 'react';
import { usePathname, useRouter } from 'next/navigation';
import { clientFetch } from '@/lib/fetch/fetch.client';
import { api } from '@/lib/api-definition';
import { useDispatch } from 'react-redux';
import { authActions } from '@/store/slices/auth.slice';
import { useIsAuthenticated } from '@/hooks/useIsAuthenticated';

// TokenGate isAuth T, apiCurrentUser F => don't render children, render TokenRestorer
export function TokenRestorer() {
    const [success, setSuccess] = useState(false);
    const router = useRouter();
    const dispatch = useDispatch();

    // No use
    // const pathname = usePathname();
    // const isAuth = useIsAuthenticated();
    // const isHome = pathname === '/';

    // NOTE:
    // Giả sử component có usePathname, isHome, useEffect, if()
    // Component body được thực thi trước, gồm usePathname, isHome và if()
    // if() return null, ko amount component
    // if() ko return, useEffect sẽ chạy sau khi render xong (tương đương componentDidMount)
    useEffect(() => {
        const tryRefreshToken = async () => {
            console.log(">>> TokenRestorer: tryRefreshToken");
            const apiRes = await clientFetch(api.auth.refresh());

            if (apiRes.success) {
                console.log(">>> TokenRestorer: Refresh success");
                dispatch(authActions.setCredentials(apiRes.data));
                setSuccess(true);
                router.refresh();
                // request to refresh
                // => middleware isAuth T, go through isPublic F
                // => TokenGate isAuth T, apiCurrentUser T, render children withAuth
            } else {
                console.warn(">>> TokenRestorer: Refresh failed, clearing auth");
                dispatch(authActions.clear());
                setSuccess(false);
                router.refresh();
                // request to refresh
                // => middleware isAuth F, decide to redirect isPublic F
                // => TokenGate isAuth F, render children noAuth
            }
        };

        tryRefreshToken();
    }, [router, dispatch]); // No use: [router, pathname, dispatch, isAuth, isHome]);

    return (
        <div>
            TokenRestorer: {success
                ? "Đã khôi phục trạng thái xác thực thành công."
                : "Thất bại khôi phục trạng thái xác thực."}
        </div>
    );
}
