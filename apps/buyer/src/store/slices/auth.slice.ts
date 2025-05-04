import { TAuthResponse, TUser } from "@/types/identity.types";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

/* NOTE:
 * Mặc dù nó giống AuthResponse, nhưng bạn nên tách riêng vì:
 * - Có thể state chứa thêm field như loading, error, v.v. // TODO
 * - Đảm bảo Redux state tự chủ, không phụ thuộc API chặt chẽ.
 */
const initialState: TAuthResponse = {
    isAuthenticated: false,
    user: {} as TUser,
    accessToken: '',
};

/* NOTE: Redux Toolkit
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
            action: PayloadAction<TAuthResponse>
        ) => {
            state.isAuthenticated = false;
            state.user = action.payload.user;
            state.accessToken = action.payload.accessToken;
        },
        logout: (state) => {
            state = initialState;
        },
    },
});

export const authReducer = authSlice.reducer;
export const authActions = authSlice.actions;
