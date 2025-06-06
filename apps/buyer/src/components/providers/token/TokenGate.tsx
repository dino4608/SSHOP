// app/auth/TokenRefresher.tsx
import { api } from "@/lib/api";
import { serverFetch } from "@/lib/fetch/fetch.server";
import { Fragment, Suspense } from "react";
import { TokenRestorer } from "./TokenRestorer";
import { getIsAuthenticated } from "@/hooks/getIsAuthenticated";

export const TokenGate = async ({ children }: { children: React.ReactNode }) => {
    // Nếu không có accessToken → không xác thực → render children
    const isAuthenticated = await getIsAuthenticated();
    if (!isAuthenticated) {
        console.log(">>> TokenGate: isAuthenticated F: render children with no auth");
        return <Fragment>{children}</Fragment>;
    }

    // Kiểm tra token hợp lệ bằng cách gọi API 'current'
    const apiRes = await serverFetch(api.auth.getCurrentUser());
    console.log(">>> TokenGate: apiRes: ", apiRes);

    // Nếu token hết hạn → gọi TokenRestorer
    if (!apiRes.success && apiRes.code === 1010) {
        console.log(">>> TokenGate: Access token expired, render TokenRestorer");
        return (
            <Suspense fallback={<div>TokenGate: Khôi phục trạng thái đã xác thực...</div>}>
                <TokenRestorer />
            </Suspense>
        );
    }

    // Nếu hợp lệ → render children
    if (apiRes.success) {
        return <Fragment>{children}</Fragment>;
    }

    // Mặc định fallback nếu có lỗi khác → no render children
    console.warn(">>> TokenGate: Unknown error, no render");
    return <Fragment>{'>>> TokenGate: Unknown error: no render'}</Fragment>;

}