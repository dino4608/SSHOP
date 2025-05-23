import { StoreProvider } from "@/components/providers/store/StoreProvider";
import { TokenAuthProvider } from "@/components/providers/token/TokenAuthProvider";
import { GlobalDataProvider } from "@/components/providers/data/GlobalDataProvider";

export const AppProvider = ({ children }: { children: React.ReactNode }) => {
    return (
        <StoreProvider>
            <TokenAuthProvider>
                <GlobalDataProvider>
                    {children}
                </GlobalDataProvider>
            </TokenAuthProvider>
        </StoreProvider>
    )
}