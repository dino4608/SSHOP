// app/components/ClientTokenRefresher.tsx
'use client';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';

export function TokenRestorer() {
    const router = useRouter();

    useEffect(() => {
        const refreshToken = async () => {
            const refreshRes = await fetch('/api/auth/refresh', {
                method: 'POST',
            });

            if (refreshRes.ok) {
                // Nếu refresh thành công, reload lại trang hoặc chuyển hướng
                router.refresh(); // Sử dụng Next.js 13+ để reload lại route hiện tại
            } else {
                // Xử lý khi không refresh được
                console.error('Refresh token không thành công');
            }
        };

        refreshToken();
    }, [router]);

    return (<div>Đang làm mới token...</div>);
}
