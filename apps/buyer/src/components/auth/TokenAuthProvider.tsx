import { TokenGate } from "./TokenGate"
import { TokenRefresher } from "./TokenRefresher"


export const TokenAuthProvider = ({ children }: { children: React.ReactNode }) => {

    return (
        <TokenGate>

            {children}

            <TokenRefresher />

        </TokenGate>
    )
}