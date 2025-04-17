"use client"
import { useEffect, useState } from "react"

export function CountdownTimer() {
    const [timeLeft, setTimeLeft] = useState(18 * 60 + 45) // 13 phút 45 giây

    useEffect(() => {
        if (timeLeft <= 0) return

        const interval = setInterval(() => {
            setTimeLeft((prev) => prev - 1)
        }, 1000)

        return () => clearInterval(interval)
    }, [timeLeft])

    const formatTime = (seconds: number) => {
        const min = Math.floor(seconds / 60)
        const sec = seconds % 60
        return `${String(min).padStart(2, '0')}:${String(sec).padStart(2, '0')}`
    }

    return (
        <p className='text-xs text-gray-500 mb-4'>
            🕒 Offer expires in: {formatTime(timeLeft)}
        </p>
    )
}

export default CountdownTimer;