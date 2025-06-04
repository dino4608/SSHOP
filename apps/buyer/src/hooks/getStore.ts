// src/lib/server/data-access/cart.ts
import { api } from '@/lib/api';
import { serverFetch } from '@/lib/fetch/fetch.server';
import { TAddress } from '@/types/address.types';
import { TUser } from '@/types/auth.types';
import { cache } from 'react';
import { TCart } from '@/types/cart.types';

export const getCurrentUser = cache(async (): Promise<TUser> => {
    const res = await serverFetch(api.auth.getCurrentUser());
    if (!res.success) console.error(">>> getCurrentUser: error: ", res.error);
    return res.data;
});

export const getDefaultAddress = cache(async (): Promise<TAddress> => {
    const res = await serverFetch(api.addresses.getDefault());
    if (!res.success) console.error(">>> getDefaultAddress: error: ", res.error);
    return res.data;
});

export const getCachedUserCart = cache(async (): Promise<TCart | null> => {
    const res = await serverFetch<TCart>(api.carts.getCart());

    if (res.success) {
        console.log(">>> getCachedUserCart: Success");
        return res.data;
    } else {
        console.warn(">>> getCachedUserCart: API failed with code:", res.code, res.error);
        return null;
    }
});