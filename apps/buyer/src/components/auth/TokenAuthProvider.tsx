import { TokenGate } from "./TokenGate"
import { TokenAutoRefresher } from "./TokenAutoRefresher"


export const TokenAuthProvider = ({ children }: { children: React.ReactNode }) => {

    return (
        <TokenGate>

            {children}

            <TokenAutoRefresher />

        </TokenGate>
    )
}