'use client';

import LoadingSuspense from '@/components/layout/LoadingSuspense';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert';
import { api } from '@/api';
import { useAppDispatch } from '@/store/hooks';
import { authActions } from '@/store/slices/auth.slice';
import { AlertCircle, Loader2 } from 'lucide-react';
import Link from 'next/link';
import { useRouter, useSearchParams } from 'next/navigation';
import { useEffect, useState, useTransition } from 'react';

const GoogleAuthPage = () => {
    const searchParams = useSearchParams();
    const router = useRouter();
    const dispatch = useAppDispatch();

    const [isPending, startTransition] = useTransition();
    const [error, setError] = useState('');

    useEffect(() => {
        const code = searchParams.get('code');

        if (code) {
            startTransition(async () => {
                const result = await api.auth.loginOrSignupWithGoogle({ code })

                if (!result.success) {
                    setError(result.error);
                } else {
                    dispatch(authActions.setCredentials(result.data));
                    router.push('/');
                }
            });
        } else {
            setError('Không tìm thấy mã xác thực từ Google.');
        }
    }, [searchParams, router]);

    return (
        <div className="flex items-center justify-center h-[90vh] px-4">
            <div className="max-w-md w-full space-y-6 text-center">
                {isPending ? (
                    <>
                        <Loader2 className="mx-auto h-8 w-8 animate-spin text-muted-foreground" />
                        <h2 className="text-lg font-semibold">Đang xác thực với Google...</h2>
                        <p className="text-sm text-gray-500">Vui lòng đợi trong giây lát.</p>
                    </>
                ) : error && (
                    <Alert variant="destructive">
                        <AlertCircle className="h-4 w-4" />
                        <AlertTitle>Lỗi xác thực với Google</AlertTitle>
                        <AlertDescription>
                            <p>{error}</p>
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
                )}
            </div>
        </div>
    );
};

const WrappedGoogleAuthPage = () => {
    return (
        <LoadingSuspense>
            <GoogleAuthPage />
        </LoadingSuspense>
    );
}

export default WrappedGoogleAuthPage;
