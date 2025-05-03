// src/store/index.ts

// Version redux store without localStorage
// import { configureStore } from "@reduxjs/toolkit";
// import userReducer from "./slices/userSlice";

// export const store = configureStore({
//     reducer: {
//         user: userReducer,
//     },
// });

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
import { authReducer } from "./slices/auth.slice";

const rootReducer = combineReducers({
    auth: authReducer,
});

const persistConfig = {
    key: "root",
    version: 1,
    storage,
};

// persistReducer là wrap lại rootReducer để tích hợp lưu trữ.
// Nó thêm logic tự động: load from localStorage → inject vào store khi app mở lại
const persistedReducer = persistReducer(persistConfig, rootReducer);

// Redux store chuẩn dùng cho ứng dụng
export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        // Mục đích: Bỏ qua warning "non-serializable data" của redux-persist.
        serializableCheck: {
            ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
        },
    }),
});

// Dùng để theo dõi việc lưu và khôi phục state từ storage (localStorage)
export const persistor = persistStore(store);
