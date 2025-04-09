import { useState, useEffect } from "react";

const useCountdown = (initialTime: number) => {
    const [timeLeft, setTimeLeft] = useState(initialTime);

    useEffect(() => {
        if (timeLeft <= 0) return;

        const interval = setInterval(() => {
            setTimeLeft((prev) => prev - 1);
        }, 1000);

        return () => clearInterval(interval);
    }, [timeLeft]);

    // Tách giờ, phút, giây từ total time (seconds)
    const hours = Math.floor(timeLeft / 3600);  // 1 giờ = 3600 giây
    const minutes = Math.floor((timeLeft % 3600) / 60);
    const seconds = timeLeft % 60;

    const formattedTime = `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;

    return { formattedTime, hours, minutes, seconds };
}

export default useCountdown;
