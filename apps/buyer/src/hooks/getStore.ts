import { cache } from 'react';
import { api } from '@/lib/api';
import { serverFetch } from '@/lib/fetch/fetch.server';
import { TUser } from '@/types/auth.types';
import { TAddress } from '@/types/address.types';

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