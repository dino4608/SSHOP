import clientLocalStorage from "@/lib/utils/clientLocalStorage";
import { useAppSelector } from "@/store/hooks";

/* NOTE:
 * Rất nên tạo hook useAuth như bạn đang làm, dù logic có vẻ đơn giản — vì:
 * - Nếu sau này bạn thay đổi cách lấy auth (ví dụ: thêm loading, lấy từ Context, hoặc từ một thư viện khác),
 * bạn chỉ cần sửa trong useAuth, không cần đi sửa khắp nơi.
 * -  Tăng khả năng tái sử dụng và đọc code dễ hơn, tên chuẩn hóa
 */
export const useCurrentUser = () => {

    let currentUser = useAppSelector(state => state.auth.currentUser);

    if (!currentUser) {
        currentUser = clientLocalStorage.get('currentUser');
    }

    return currentUser;
}