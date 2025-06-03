// components/providers/data/GlobalDataInitializer.tsx
import { getCachedUserCart, getCurrentUser, getDefaultAddress } from '@/hooks/getStore';
import { getIsAuthenticated } from "@/hooks/getIsAuthenticated";
import { Fragment } from 'react';
import { GlobalDataHydrator } from './GlobalDataHydrator';

export const GlobalDataInitializer = async ({ children }: { children: React.ReactNode }) => {
    const isAuthenticated = await getIsAuthenticated();

    if (!isAuthenticated) {
        console.log(">>> GlobalDataInitializer: don't init.");
        return <Fragment>{children}</Fragment>;
    }

    const [currentUser, defaultAddress, cart] = await Promise.all([
        getCurrentUser(),
        getDefaultAddress(),
        getCachedUserCart(),
    ]);

    return (
        <Fragment>
            {children}
            <GlobalDataHydrator
                currentUser={currentUser}
                defaultAddress={defaultAddress}
                cart={cart}
            />
        </Fragment>
    );
};