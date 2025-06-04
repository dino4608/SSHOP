// lib/server-action.ts
'use server';
import { serverFetch } from "@/lib/fetch/fetch.server";
import { TApiDefinition } from "@/types/base.types";

export async function serverAction<T>(api: TApiDefinition<T>) {
    return await serverFetch(api);
}