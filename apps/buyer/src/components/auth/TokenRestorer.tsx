// app/components/ClientTokenRefresher.tsx
'use client';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { api } from '@/api';

export function TokenRestorer() {
    const router = useRouter();

    useEffect(() => {
        const refreshToken = async () => {
            // call next api
            // const refreshRes = await fetch('/api/auth/refresh', {
            //     method: 'POST',
            // });

            // call direct api
            const apiRes = await api.auth.refresh();

            if (apiRes.success) {
                // reload lại route hiện tại (Next.js 13+)
                router.refresh();
            } else {
                // Xử lý khi không refresh được
                console.log('>>> TokenRestorer: Refresh token không thành công');
            }
        };

        refreshToken();
    }, [router]);

    return (<div>Đang làm mới token...</div>);
}
