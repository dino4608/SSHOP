import { StoreProvider } from "@/store/provider";
import { TokenAuthProvider } from "../token/TokenAuthProvider";

export const AppProvider = ({ children }: { children: React.ReactNode }) => {

    return (
        <StoreProvider>
            <TokenAuthProvider>
                {children}
            </TokenAuthProvider>
        </StoreProvider>
    )
}