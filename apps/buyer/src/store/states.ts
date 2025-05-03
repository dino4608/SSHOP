import { RootState } from "./hooks";

const globalState = {
    // authStates
    user: (state: RootState) => state.auth.user,
    accessToken: (state: RootState) => state.auth.accessToken,
}

export default globalState;