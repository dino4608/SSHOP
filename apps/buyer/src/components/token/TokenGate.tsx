// app/auth/TokenRefresher.tsx
import { api } from "@/lib/api";
import { serverFetch } from "@/lib/fetch/fetch.server";
import { Fragment, Suspense } from "react";
import { TokenRestorer } from "./TokenRestorer";
import { asyncIsAuthenticated } from "@/lib/server/auth";

export const TokenGate = async ({ children }: { children: React.ReactNode }) => {

    const isAuthenticated = await asyncIsAuthenticated();

    // Nếu không có accessToken → xác định là không xác thực → render children luôn
    if (!isAuthenticated) {
        console.log(">>> TokenGate: isAuthenticated F: render children with no auth");
        return <Fragment>{children}</Fragment>;
    }

    // Kiểm tra token hợp lệ bằng cách gọi API 'current'
    const apiRes = await serverFetch(api.auth.getCurrentUser());

    console.log(">>> TokenGate: apiRes: ", apiRes);

    // Nếu token hết hạn → gọi TokenRestorer
    if (!apiRes.success && apiRes.code === 1200) {
        console.log(">>> TokenGate: Access token expired, render TokenRestorer");

        return (
            <Suspense fallback={<div>TokenGate: Khôi phục trạng thái đã xác thực...</div>}>
                <TokenRestorer />
            </Suspense>
        );
    }

    // Nếu hợp lệ → render children
    if (apiRes.success) {
        const currentUser = apiRes.data;
        console.log(">>> TokenGate: currentUser: ", currentUser);
        return <Fragment>{children}</Fragment>; // update Redux store
    }

    // Mặc định fallback nếu có lỗi khác → render children luôn
    console.warn(">>> TokenGate: Unknown error, no render");
    return <Fragment>{'>>> TokenGate: Unknown error: no render'}</Fragment>;

}