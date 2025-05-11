// app/auth/TokenRefresher.tsx
import { api } from "@/api";
import { Fragment, Suspense } from "react";
import { TokenRestorer } from "./TokenRestorer";

export const TokenGate = async ({ children }: { children: React.ReactNode }) => {

    // Kiểm tra token hợp lệ bằng cách gọi API 'current'
    const apiRes = await api.auth.getCurrentUser();

    console.log('>>> TokenGate: apiRes: ' + apiRes);

    // Nếu token hết hạn, trả về loading refresher
    if (!apiRes.success) {
        return (
            <Suspense fallback={<div>Đang làm mới token...</div>}>
                <TokenRestorer />
            </Suspense>
        );
    }

    // Token hợp lệ, render children bình thường
    // TODO #1: update Redux store
    const currentUser = apiRes.data;

    console.log('>>> TokenGate: currentUser: ' + currentUser);

    return (
        <Fragment>
            {currentUser.name || currentUser.email || currentUser.username}
            {/*{children} */}
        </Fragment>
    );
}