// src/store/slices/cart.slice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { TCart } from "@/types/cart.types";
import clientLocal from "@/lib/storage/local.client";
import { CART } from "@/lib/constants";

type TCartState = {
    cart: TCart | null;
};

const initialState: TCartState = {
    cart: clientLocal.get(CART) || null,
};

const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: {
        setCart: (state, { payload: cart }: PayloadAction<TCart | null>) => {
            if (cart) {
                clientLocal.set(CART, cart);
                state.cart = cart;
            } else {
                clientLocal.remove(CART);
                state.cart = null;
            }
        },
        clear: () => {
            clientLocal.remove(CART);
            return { cart: null };
        },
    },
});

export const cartReducer = cartSlice.reducer;
export const cartActions = cartSlice.actions;