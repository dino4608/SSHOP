import { Session } from "inspector/promises";

type User = {
    id: number;
    email: string;
    passwordHash: string;
    sessions: Session[]; // Cần định nghĩa Session ở đâu đó
    // cart?: Cart | null;   // Vì cart là optional
};

export default User;