"use client";
import { store } from "@/store";
import React from "react";
import { Provider as ReduxProvider } from "react-redux";

export const StoreProvider = ({ children }: { children: React.ReactNode }) => {
    return (
        <ReduxProvider store={store}>
            {children}
        </ReduxProvider>
    );
}