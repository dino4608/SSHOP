import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import type { store } from "./index";

// NOTE:
// Types of dispatch and state of Store
// RootState: Nó đại diện cho toàn bộ kiểu của Redux store, được tạo tự động từ các reducer bạn đã cấu hình.
// typeof store.getState: lấy Type của hàm
// ReturnType: lấy Type trả về của hàm
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

// Custom hooks + ts type
export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
