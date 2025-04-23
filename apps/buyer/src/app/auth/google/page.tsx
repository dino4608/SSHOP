'use client';

import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert';
import action from '@/server-actions';
import { initActionState } from '@/server-actions/utils';
import { AlertCircle, Loader2 } from 'lucide-react';
import Link from 'next/link';
import { useRouter, useSearchParams } from 'next/navigation';
import { Suspense, useEffect, useState, useTransition } from 'react';

const GoogleAuthPage = () => {
    const searchParams = useSearchParams();
    const router = useRouter();

    const [isPending, startTransition] = useTransition();
    const [error, setError] = useState(initActionState);

    useEffect(() => {
        const code = searchParams.get('code');

        if (code) {
            startTransition(async () => {
                const result = await action.auth.logInOrSignUpWithGoogle({ code });

                if (!result.success) {
                    setError(result);
                    console.error(result.message);
                } else {
                    // TODO #1
                    // - process AuthResponse
                    // - set token
                    // - set logged in
                    router.push('/');
                }
            });
        } else {
            setError({ success: false, message: 'Không tìm thấy mã xác thực từ Google.', data: {} });
        }
    }, [searchParams, router]);

    // TODO #1: toast error and navigate to look up identifier

    return (
        <div className="flex items-center justify-center h-[90vh] px-4">
            <div className="max-w-md w-full space-y-6 text-center">
                {isPending ? (
                    <>
                        <Loader2 className="mx-auto h-8 w-8 animate-spin text-muted-foreground" />
                        <h2 className="text-lg font-semibold">Đang xác thực với Google...</h2>
                        <p className="text-sm text-gray-500">Vui lòng đợi trong giây lát.</p>
                    </>
                ) : error.message && (
                    <Alert variant="destructive">
                        <AlertCircle className="h-4 w-4" />
                        <AlertTitle>Lỗi xác thực với Google</AlertTitle>
                        <AlertDescription>
                            <p>{error.message}</p>
                            <p>
                                <Link
                                    href="/auth"
                                    className="text-sm font-medium text-blue-600 hover:underline"
                                >
                                    ← Quay lại
                                </Link>
                            </p>
                        </AlertDescription>
                    </Alert>

                    // <Alert variant="destructive">
                    //     <AlertTitle>Lỗi đăng nhập</AlertTitle>
                    //     <AlertDescription>
                    //         <p>{error.message}</p>
                    //         <p>
                    //             <Link
                    //                 href="/auth"
                    //                 className="text-sm font-medium text-blue-600 hover:underline"
                    //             >
                    //                 Quay lại để nhập email hoặc username
                    //             </Link>
                    //         </p>
                    //     </AlertDescription>
                    // </Alert>
                )}
            </div>
        </div>
    );
};

export default function OutboundGoogleAuthPage() {
    return (
        <Suspense fallback={<div className="text-center mt-8">Đang tải...</div>}>
            <GoogleAuthPage />
        </Suspense>
    );
}
