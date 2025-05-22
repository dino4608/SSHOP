function getEnvSafely(value: string | undefined, name: string): string {
    if (value) return value;
    throw new Error(`>>> getEnv: Missing ${name}`);
}

export const env = {
    BACKEND_URL: getEnvSafely(process.env.NEXT_PUBLIC_BACKEND_URL, 'NEXT_PUBLIC_BACKEND_URL'),
    FILES_ENDPOINT: getEnvSafely(process.env.NEXT_PUBLIC_FILES_ENDPOINT, 'NEXT_PUBLIC_FILES_ENDPOINT'),
};