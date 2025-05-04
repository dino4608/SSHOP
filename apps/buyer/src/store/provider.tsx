// src/store/provider.tsx
"use client";

import { Provider } from "react-redux";
import { store, persistor } from "./index";
import { PersistGate } from "redux-persist/integration/react";
import React from "react";

export function ReduxProvider({ children }: { children: React.ReactNode }) {
    // EXP: PersistGate:
    // - Chặn render children cho đến khi persistor (localStorage) load xong.
    // - Khi đang rehydrate, nếu bạn đặt loading={null}, nó không hiển thị gì cả (trống rỗng).
    return (
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                {children}
            </PersistGate>
        </Provider>
    );
}
