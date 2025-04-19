'use client';

import useCountdown from '@/hooks/useCountDown';
import { Loader2 } from 'lucide-react';
import Form from 'next/form';
import React, { useActionState } from 'react';

const initialState = {
    message: '',
};

interface SignUpProps {
    action: (prevState: any, formData: FormData) => Promise<{ message: string; } | undefined>;
}

const SignUp: React.FC<SignUpProps> = ({ action }: SignUpProps) => {
    const [_state, formAction, isPending] = useActionState(action, initialState); //it only work at the client side

    const { formattedTime } = useCountdown(60 * (60 * 6 + 18) + 45); // 6h 18m 45s

    return (
        <Form action={formAction} className='max-w-md mx-auto my-16 p-8 bg-white border border-gray-200 rounded-lg shadow-md'>
            <h1 className='text-2xl font-bold text-center mb-2'>
                Join the DEAL Revolution!
            </h1>
            {/* text-rose-600 */}
            <p className='text-center text-sm text-[var(--dino-red-1)] font-semibold mb-2'>
                🔥 LIMITED TIME OFFER 🔥
            </p>
            <p className='text-center text-sm text-gray-600 mb-6'>
                Sign up now and get 90% OFF your first order!
            </p>

            <div className='space-y-6'>
                {/* Email */}
                <div className='space-y-2'>
                    <label htmlFor='email' className='block text-sm font-medium text-gray-700'>
                        Email address
                    </label>
                    <input
                        type='email'
                        id='email'
                        name='email' // to link to the form action
                        autoComplete='email' // the auto-fills form fields with saved data
                        required
                        className='w-full px-4 py-3 border border-gray-200 rounded-md focus:ring-2 focus:ring-black focus:border-transparent transition-colors'
                        placeholder='Enter your email'
                    />
                </div>

                {/* Password */}
                <div className='space-y-2'>
                    <label htmlFor='password' className='block text-sm font-medium text-gray-700'>
                        Password
                    </label>
                    <input
                        type='password'
                        id='password'
                        name='password'
                        autoComplete='new-password' //the auto-fills form fields with saved data
                        required
                        className='w-full px-4 py-3 border border-gray-200 rounded-md focus:ring-2 focus:ring-black focus:border-transparent transition-colors'
                        placeholder='Create a password'
                    />
                </div>

                {/* Copywriting */}
                <div className='text-center'>
                    <p className='text-xs text-gray-500 mb-2'>
                        ⚡️ Only 146 welcome bonus packages remaining!
                    </p>
                    <p className='text-xs text-gray-500 mb-4'>
                        🕒 Offer expires in: {formattedTime}
                    </p>
                </div>

                {/* Submit Button */}
                {/* text-rose-600 hover:bg-rose-700 */}
                <button
                    type='submit'
                    disabled={isPending}
                    className={`w-full bg-[var(--dino-red-1)] text-white py-3 rounded-md hover:bg-rose-600 transition-colors font-medium flex items-center justify-center gap-2 ${isPending ? 'cursor-not-allowed' : ''}`}
                >
                    {isPending ? (
                        <React.Fragment>
                            <Loader2 className='h-4 w-4 animate-spin' />
                            CREATING ACCOUNT...
                        </React.Fragment>
                    ) : (
                        'CREATE ACCOUNT'
                    )}
                </button>
            </div>
        </Form>
    );
};

export default SignUp;
