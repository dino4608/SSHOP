import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import type { TAppDispatch, TRootState } from "./index";

// Custom hooks + ts type
// useAppDispatch() sẽ trả về hàm dispatch có kiểu chuẩn từ store (ko có đầu vào).
// useAppDispatch() sẽ trả về biến state có kiểu chuẩn từ store (đầu vào là 1 selector function).
export const useAppDispatch: () => TAppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<TRootState> = useSelector;