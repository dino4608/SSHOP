// src/store/provider.tsx
"use client";

import { Provider } from "react-redux";
import { store, persistor } from "./index";
import { PersistGate } from "redux-persist/integration/react";
import React from "react";

export function ReduxProvider({ children }: { children: React.ReactNode }) {
    return (
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                {children}
            </PersistGate>
        </Provider>
    );
}
