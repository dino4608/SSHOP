// lib/server-action.ts
'use server';

import { TApiDefinition } from "@/lib/api-definition/config";
import { serverFetch } from "@/lib/fetch/fetch.server";

// TODO #1: move to serverAction() wrapper
export async function serverAction<T>(apiDef: TApiDefinition<T>) {
    return await serverFetch(apiDef);
}