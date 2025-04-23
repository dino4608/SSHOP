import { Session } from "inspector/promises";

export type XTUser = {
    id: number;
    email: string;
    passwordHash: string;
    sessions: Session[]; // Cần định nghĩa Session ở đâu đó
    // cart?: Cart | null;   // Vì cart là optional
};