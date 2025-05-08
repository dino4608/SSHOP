import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export const timeout = async (seconds: number) => {
  await new Promise<void>((resolve) => setTimeout(() => resolve(), seconds * 1000));
}

export * from './clientLocalStorage';

export * from './clientCookies';

export const isBrowser = typeof window !== "undefined";
