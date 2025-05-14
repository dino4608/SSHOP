
import { TokenAutoRefresher } from "./TokenAutoRefresher"
import { TokenGate } from "./TokenGate"


export const TokenAuthProvider = ({ children }: { children: React.ReactNode }) => {

    return (
        <TokenGate>
            {children}
            <TokenAutoRefresher />
        </TokenGate>
    )
}