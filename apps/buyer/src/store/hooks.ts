import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import type { TAppDispatch, TRootState } from "./index";

// EXP:
// Custom hooks + ts type
// useAppDispatch() sẽ trả về hàm dispatch có kiểu chuẩn từ store (ko có đầu vào).
// useAppDispatch() sẽ trả về biến state có kiểu chuẩn từ store (đầu vào là 1 selector function).

// NOTE: dispatch(action)
// Phase 1: Redux Store	  ✅ Cập nhật ngay lập tức → store.getState() sau đó sẽ trả về giá trị mới.
// Phase 2: React UI	  🚫 Chưa render lại ngay. React mark as dirty các component đang dùng giá trị Redux đó (useSelector, connect,...) để re-render trong batch render tiếp theo.
// Phase 3: useEffect end 🌀 React mới bắt đầu chạy phase re-render với state mới đã được cập nhật.

export const useAppDispatch: () => TAppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<TRootState> = useSelector;