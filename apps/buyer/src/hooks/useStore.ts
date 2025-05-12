import clientLocal from "@/lib/storage/local.client";
import { useAppSelector } from "@/store/hooks";
import { TUser } from "@/types/identity.types";
import { useIsAuthenticated } from "./useIsAuthenticated";
import { CURRENT_USER } from "@/lib/constants";

/* NOTE:
 * Rất nên tạo hook useAuth như bạn đang làm, dù logic có vẻ đơn giản — vì:
 * - Nếu sau này bạn thay đổi cách lấy auth (ví dụ: thêm loading, lấy từ Context, hoặc từ một thư viện khác),
 * bạn chỉ cần sửa trong useAuth, không cần đi sửa khắp nơi.
 * -  Tăng khả năng tái sử dụng và đọc code dễ hơn, tên chuẩn hóa
 */

/* NOTE:
 * || : falsy
 * ?? : nullish
 */

/* NOTE: hooks
 * Hooks phải luôn được gọi ở cấp cao nhất trong function component hoặc custom hook
 * không được gọi conditionally.
 */
export const useCurrentUser = () => {
    const isAuthenticated = useIsAuthenticated();
    const currentUser = useAppSelector(state => state.auth.currentUser);

    if (!isAuthenticated) return null;
    return currentUser || clientLocal.get<TUser>(CURRENT_USER);
}