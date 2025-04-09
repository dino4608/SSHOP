'use client';

import GoogleOnlyStep from '@/components/auth/GoogleOnlyStep';
import SignInStep from '@/components/auth/SignInStep';
import SignUpStep from '@/components/auth/SignUpStep';
import UsernameStep from '@/components/auth/UsernameStep';
import { checkUsername } from '@/services/identity/auth.service';
import { useState } from 'react';


export default function AuthPage() {
    const [username, setUsername] = useState('');
    const [step, setStep] = useState<'initial' | 'sign-in' | 'sign-up' | 'google-only'>('initial');

    const handleUsernameSubmit = async (name: string) => {
        try {
            const res = await checkUsername(name);

            setUsername(name);

            if (res.exists) {
                if (res.hasPassword) {
                    setStep('sign-in');
                } else {
                    setStep('google-only');
                }
            } else {
                setStep('sign-up');
            }
        } catch (err: any) {
            // toast.error(err.message || 'Lỗi kiểm tra username');
        }
    };

    const handleReset = () => {
        setUsername('');
        setStep('initial');
    };

    return (
        <div className="max-w-md mx-auto mt-10 space-y-6">
            {step === 'initial' && (
                <UsernameStep onSubmit={handleUsernameSubmit} />
            )}
            {step === 'sign-in' && (
                <SignInStep username={username} onUsernameChange={handleReset} />
            )}
            {step === 'sign-up' && (
                <SignUpStep username={username} onUsernameChange={handleReset} />
            )}
            {step === 'google-only' && (
                <GoogleOnlyStep username={username} onUsernameChange={handleReset} />
            )}
        </div>
    );
}
