// components/providers/data/GlobalDataInitializer.tsx
import { getCurrentUser, getDefaultAddress } from '@/hooks/getStore';
import { getIsAuthenticated } from "@/hooks/getIsAuthenticated";
import { Fragment } from 'react';
import { GlobalDataHydrator } from './GlobalDataHydrator';

export const GlobalDataInitializer = async ({ children }: { children: React.ReactNode }) => {
    const isAuthenticated = await getIsAuthenticated();

    if (!isAuthenticated) {
        console.log(">>> GlobalDataInitializer: don't init.");
        return <Fragment>{children}</Fragment>;
    }

    const [currentUser, defaultAddress] = await Promise.all([
        getCurrentUser(),
        getDefaultAddress(),
    ]);

    return (
        <Fragment>
            {children}
            <GlobalDataHydrator
                currentUser={currentUser}
                defaultAddress={defaultAddress}
            />
        </Fragment>
    );
};