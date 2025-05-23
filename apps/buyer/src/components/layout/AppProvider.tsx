import { StoreProvider } from "@/store/StoreProvider";
import { TokenAuthProvider } from "../token/TokenAuthProvider";
import { GlobalDataProvider } from "../providers/data/GlobalDataProvider";


export const AppProvider = ({ children }: { children: React.ReactNode }) => {

    return (
        <StoreProvider>
            <TokenAuthProvider>
                {/* {children} */}
                <GlobalDataProvider>
                    {children}
                </GlobalDataProvider>
            </TokenAuthProvider>
        </StoreProvider>
    )
}