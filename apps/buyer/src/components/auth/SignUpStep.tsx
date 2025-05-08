'use client';

import { Button } from '@/components/ui/button';
import {
    Form,
    FormControl,
    FormField, FormItem, FormLabel,
    FormMessage
} from "@/components/ui/form";
import { Input } from '@/components/ui/input';
import { api } from '@/api';
import { useAppDispatch } from '@/store/hooks';
import { authActions } from '@/store/slices/auth.slice';
import { logInFormSchema, TLogInFormData } from "@/validations/auth.validations";
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useState, useTransition } from 'react';
import { useForm } from 'react-hook-form';
import FormButtonSubmit, { FormError } from '../ui/custom/form';
import ButtonAuthGoogle from './ButtonAuthGoogle';

type Props = {
    email: string;
    onEmailChange: () => void;
};

export default function SignUpStep({ email, onEmailChange }: Props) {
    // TODO #1:
    // data: sign in on the server + use auth to update the redux store

    const form = useForm<TLogInFormData>({
        resolver: zodResolver(logInFormSchema),
        defaultValues: { password: '' },
    });
    const router = useRouter();
    const dispatch = useAppDispatch();

    const [isPending, startTransition] = useTransition();
    const [error, setError] = useState('');

    const onSubmit = ({ password }: TLogInFormData) => {
        startTransition(async () => {
            const result = await api.auth.signupWithPassword({ email, password })

            if (!result.success) {
                setError(result.error);
            } else {
                dispatch(authActions.setCredentials(result.data));
                router.push('/');
            }
        });
    };

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="component-step">
                <div className="text-sm">Không tìm thấy tài khoản <strong>{email}</strong>, mời bạn tạo mới</div>

                <FormField
                    control={form.control}
                    name="password"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Mật khẩu</FormLabel>
                            <FormControl>
                                <Input type="password" placeholder="Nhập mật khẩu" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />

                <FormButtonSubmit className='btn-primary' isPending={isPending}>Đăng ký</FormButtonSubmit>
                <div className="text-or">Hoặc</div>
                <ButtonAuthGoogle className='btn-second'>Đăng ký với Google</ButtonAuthGoogle>
                <Button variant="ghost" className="btn-second" onClick={onEmailChange}>← Thay đổi email</Button>

            </form>

            <FormError message={error} />
        </Form>
    );
}
