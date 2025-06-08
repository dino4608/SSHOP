// app/auth/TokenRefresher.tsx
import { api } from "@/lib/api";
import { serverFetch } from "@/lib/fetch/fetch.server";
import { Fragment, Suspense } from "react";
import { TokenRestorer } from "./TokenRestorer";
import { getIsAuthenticated } from "@/functions/getIsAuthenticated";

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
            <Suspense fallback={
                <div className="container mx-auto p-4 flex justify-center items-center min-h-[calc(100vh-150px)]">
                    <div className="text-center text-lg text-gray-600">Đang khôi phục trạng thái đã xác thực...</div>
                </div>
            }>
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
    return (
        <div className="container mx-auto p-4 flex justify-center items-center min-h-[calc(100vh-150px)]">
            <div className="text-center text-lg text-red-600">
                Có lỗi xảy ra: Lỗi không xác định. Vì thế, không tiếp tục render.
            </div>
        </div>
    );

}