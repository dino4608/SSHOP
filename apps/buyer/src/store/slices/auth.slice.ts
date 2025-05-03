import clientLocalStorage from "@/lib/utils/clientLocalStorage";
import { TAuthResponse, TUser } from "@/types/identity.types";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

/* NOTE:
 * Mặc dù nó Type of AuthState giống AuthResponse, nhưng bạn nên tách riêng vì:
 * - Có thể state chứa thêm field như loading, error, v.v. // TODO
 * - Đảm bảo Redux state tự chủ, không phụ thuộc API chặt chẽ.
 */
type TUserState = {
    currentUser: TUser | null;
}

const initialState: TUserState = { currentUser: null };

/* NOTE: Redux Toolkit
 * - Step: create Slide => create Reducer => add to Store.
 * - Actions: don't need to create, they auto created by Slide.
 * - Action name format: slideName/reducerName. Ex: auth/set.
 * - Mutable style: is allowed to write, Redux Toolkit attached Immer will convert them to Immutable style
 * - Immer sẽ clone state đã áp dụng các thay đổi, rồi return bản clone ngầm cho bạn.
 * - return: bỏ qua Immer, bạn tự tạo và trả về state mới.
 */
const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        setCredentials: (state, { payload, type }: PayloadAction<TAuthResponse>) => {
            console.log('>>> type: ' + type);
            clientLocalStorage.set('accessToken', payload.accessToken);
            clientLocalStorage.set('currentUser', payload.user);
            state.currentUser = payload.user;
        },
        clear: (state) => {
            return initialState;
        },
    },
});

export const authReducer = authSlice.reducer;
export const authActions = authSlice.actions;
