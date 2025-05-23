// components/providers/data/GlobalDataProvider.tsx
import { GlobalDataInitializer } from "./GlobalDataInitializer";

export const GlobalDataProvider = ({ children }: { children: React.ReactNode }) => {
    return (
        <GlobalDataInitializer>
            {children}
        </GlobalDataInitializer>
    );
};