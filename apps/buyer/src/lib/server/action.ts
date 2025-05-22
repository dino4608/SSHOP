// lib/server-action.ts
'use server';
import { serverFetch } from "@/lib/fetch/fetch.server";
import { TApiDefinition } from "@/types/base.types";

// TODO #1: move to serverAction() wrapper
export async function serverAction<T>(apiDef: TApiDefinition<T>) {
    return await serverFetch(apiDef);
}