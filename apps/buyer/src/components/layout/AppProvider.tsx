import StoreProvider from "@/store/provider"
import { TokenAuthProvider } from "../auth/TokenAuthProvider";

const AppProvider = ({ children }: { children: React.ReactNode }) => {

    return (
        <StoreProvider>
            <TokenAuthProvider>
                {children}
            </TokenAuthProvider>
        </StoreProvider>
    )
}

export default AppProvider;