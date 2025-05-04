import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// TODO #1
type User = {
    username: string;
    email: string;
    status: string;
    isEmailVerified: boolean;
    deleted: boolean;
};

type AuthState = {
    user: User | null;
    accessToken: string | null;
}

const initialState: AuthState = {
    user: null,
    accessToken: null,
};

/*
* NOTE: Redux Toolkit
* - Step: create Slide => create Reducer => add to Store.
* - Actions: don't need to create, they auto created by Slide.
* - Action name format: slideName/reducerName. Ex: auth/setCredentials.
* - Mutable style: is allowed to write, Redux Toolkit attached Immer will convert them to Immutable style
*/
const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        setCredentials: (
            state,
            action: PayloadAction<{ user: User; accessToken: string }>
        ) => {
            state.user = action.payload.user;
            state.accessToken = action.payload.accessToken;
        },
        logout: (state) => {
            state.user = null;
            state.accessToken = null;
        },
    },
});

export const authReducer = authSlice.reducer;
export const authActions = authSlice.actions;
