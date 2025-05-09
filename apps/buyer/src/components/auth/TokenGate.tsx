// app/auth/TokenRefresher.tsx
import { ACCESS_TOKEN } from "@/lib/constants";
import { cookies } from "next/headers";
import { Fragment, Suspense } from "react";
import { TokenRestorer } from "./TokenRestorer";

export const TokenGate = async ({ children }: { children: React.ReactNode }) => {

    // Lấy cookie từ request
    const serverCookies = await cookies();
    const accessToken = serverCookies.get(ACCESS_TOKEN)?.value;

    // Kiểm tra token hợp lệ bằng cách gọi API 'current'
    const res = await fetch(`${process.env.API_BASE_URL}/api/current`, {
        headers: {
            'Authorization': `Bearer ${accessToken}`,
        },
        cache: 'no-store', // Không cache, luôn gọi mới
    });

    if (res.status === 401) {
        // Nếu token hết hạn, trả về loading refresher
        return (
            <Suspense fallback={<div>Đang làm mới token...</div>}>
                <TokenRestorer />
            </Suspense>
        );
    }

    // Token hợp lệ, render children bình thường
    // TODO #1: update Redux store
    const userData = await res.json();
    return (
        <Fragment>
            {children}
        </Fragment>
    );
}