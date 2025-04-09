type Session = {
    id: string;
    userId: number;
    expiresAt: Date;
    // user: User; // Bạn cần định nghĩa type User như ở trên
};

export default Session;

