import { TRootState } from ".";

const globalStates = {
    auth: (state: TRootState) => state.auth,
    currentUser: (state: TRootState) => state.auth.user,
    accessToken: (state: TRootState) => state.auth.accessToken,
}

export default globalStates;