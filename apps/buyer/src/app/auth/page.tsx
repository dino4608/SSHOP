'use client';

import GoogleOnlyStep from '@/components/auth/GoogleOnlyStep';
import LookUpStep from '@/components/auth/LookUpStep';
import LogInStep from '@/components/auth/LogInStep';
import SignUpStep from '@/components/auth/SignUpStep';
import React, { useState } from 'react';
import './styles.css';
import { TLookUpFormData } from '@/validations/auth.validations';
import { serverActions } from '@/server-actions';
import { ReduxProvider } from '@/store/provider';

const AuthPage = () => {

    const [email, setEmail] = useState('');
    const [step, setStep] = useState<'look-up' | 'log-in' | 'sign-up' | 'google-only'>('look-up');

    const onLookup = async ({ email }: TLookUpFormData) => {
        const result = await serverActions.auth.lookupIdentifier({ email });

        if (!result.success) {
            return result;
        }

        setEmail(email);
        if (result.data.isEmailProvided) {
            setStep(result.data.isPasswordProvided ? 'log-in' : 'google-only');
        } else {
            setStep('sign-up');
        }
    };

    const onReset = () => {
        setEmail('');
        setStep('look-up');
    };

    return (
        <div className="max-w-md mx-auto my-16 p-8 bg-white border border-gray-200 rounded-lg shadow-md space-y-6">
            <div className='space-y-2'>
                <h1 className='text-2xl font-bold text-center'>
                    Nhập cuộc DEAL cực chất
                </h1>
                <p className='text-center text-sm text-[var(--dino-red-1)] font-semibold'>
                    🔥 ĐẶC QUYỀN THÀNH VIÊN 🔥
                </p>
                <p className='text-center text-sm text-gray-600'>
                    Tham gia ngay để lấy những deal ưu đãi!
                </p>
            </div>

            {step === 'look-up' && (
                <LookUpStep lookupIdentifier={onLookup} />
            )}
            {step === 'log-in' && (
                <LogInStep email={email} onEmailChange={onReset} />
            )}
            {step === 'sign-up' && (
                <SignUpStep email={email} onEmailChange={onReset} />
            )}
            {step === 'google-only' && (
                <GoogleOnlyStep email={email} onEmailChange={onReset} />
            )}
        </div>
    );
}

const WrappedAuthPage = () => {
    return (
        <ReduxProvider>
            <AuthPage />
        </ReduxProvider>
    )
}

export default WrappedAuthPage;
