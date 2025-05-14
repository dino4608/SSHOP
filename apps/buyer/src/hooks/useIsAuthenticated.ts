import { ACCESS_TOKEN } from '@/lib/constants';
import clientCookies from '@/lib/storage/cookie.client';
import { useAppSelector } from '@/store/hooks';
import { useMemo } from 'react';

// export const useIsAuthenticated = () => {
//     const isAuthRedux = useSelector((state: TRootState) => state.auth.isAuthenticated);
//     const token = cookieStore.get("ACCESS_TOKEN");

//     // TODO: explain useMemo() !
//     return useMemo(() => {
//         if (typeof isAuthRedux === 'boolean') return isAuthRedux;
//         return !!token && !!localStore.get('currentUser');
//     }, [isAuthRedux, token]);
// };

// export const useIsAuthenticated = () => {
//     const isAuthRedux = useSelector((state: TRootState) => state.auth.isAuthenticated);
//     const [token, setToken] = useState<string | null>(null);
//     const [currentUser, setCurrentUser] = useState
//         <any>(null);

//     useEffect(() => {
//         setToken(cookieStore.get("ACCESS_TOKEN"));
//         setCurrentUser(localStore.get("currentUser"));
//     }, []);

//     return useMemo(() => {
//         if (typeof isAuthRedux === 'boolean') return isAuthRedux;
//         return !!token && !!currentUser;
//     }, [isAuthRedux, token, currentUser]);
// };

export const useIsAuthenticated = () => {
    const accessToken = useAppSelector(state => state.auth.accessToken);

    return useMemo(() => {
        return Boolean(accessToken || clientCookies.get(ACCESS_TOKEN));
    }, [accessToken]);
};


