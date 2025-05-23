// src/store/index.ts
import { configureStore } from "@reduxjs/toolkit";
import { addressActions, addressReducer } from "./slices/address.slice";
import { authActions, authReducer } from "./slices/auth.slice";

export const store = configureStore({
    reducer: {
        auth: authReducer,
        address: addressReducer,
    },
});

export const actions = {
    auth: authActions,
    address: addressActions,
}

// NOTE: Type of Redux hooks
// Types of dispatch and state of Store
// RootState: Nó đại diện cho toàn bộ kiểu của Redux store, được tạo tự động từ các reducer bạn đã cấu hình.
// typeof store.getState: lấy Type của hàm
// ReturnType: lấy Type trả về của hàm
export type TRootState = ReturnType<typeof store.getState>;
export type TAppDispatch = typeof store.dispatch;