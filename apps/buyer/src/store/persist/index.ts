// src/store/index.ts
import { combineReducers, configureStore } from "@reduxjs/toolkit";
import {
    FLUSH,
    PAUSE,
    PERSIST,
    persistReducer,
    persistStore,
    PURGE,
    REGISTER,
    REHYDRATE,
} from "redux-persist";
import storage from "redux-persist/lib/storage"; // localStorage
import { authReducer } from "../slices/auth.slice";

const rootReducer = combineReducers({
    auth: authReducer,
});

export default rootReducer;


const persistConfig = {
    key: "root",
    version: 1,
    storage,
};

// EXP
// persistReducer là wrap lại rootReducer để tích hợp lưu trữ.
// Nó thêm logic tự động: load from localStorage → inject vào store khi app mở lại
const persistedReducer = persistReducer(persistConfig, rootReducer);

// Redux store chuẩn dùng cho ứng dụng
// Redux là một singleton store được tạo ra duy nhất một lần và truyền qua ReduxProvider
export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        // EXP: Mục đích: Bỏ qua warning "non-serializable data" của redux-persist.
        serializableCheck: {
            ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
        },
    }),
});

// EXP: Dùng để theo dõi việc lưu và khôi phục state từ storage (localStorage)
export const persistor = persistStore(store);

// NOTE: Type of Redux hooks
// Types of dispatch and state of Store
// RootState: Nó đại diện cho toàn bộ kiểu của Redux store, được tạo tự động từ các reducer bạn đã cấu hình.
// typeof store.getState: lấy Type của hàm
// ReturnType: lấy Type trả về của hàm
export type TRootState = ReturnType<typeof store.getState>;
export type TAppDispatch = typeof store.dispatch;