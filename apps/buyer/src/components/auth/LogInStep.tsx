'use client';

import { Button } from '@/components/ui/button';
import {
    Form,
    FormControl,
    FormField, FormItem, FormLabel,
    FormMessage
} from "@/components/ui/form";
import { Input } from '@/components/ui/input';
import action from '@/server-actions';
import { initActionState } from '@/server-actions/utils';
import { logInFormSchema, TLogInFormData } from "@/validations/auth.validations";
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { useState, useTransition } from 'react';
import { useForm } from 'react-hook-form';
import FormButtonSubmit, { FormError } from '../ui/custom/form';

type Props = {
    email: string;
    onEmailChange: () => void;
};

const LogInStep = ({ email, onEmailChange }: Props) => {
    // TODO #1:
    // data: sign in on the server + use auth to update the redux store

    const form = useForm<TLogInFormData>({
        resolver: zodResolver(logInFormSchema),
        defaultValues: { password: '' },
    });

    const [isPending, startTransition] = useTransition();

    const [error, setError] = useState(initActionState);

    const router = useRouter();

    const onSubmit = ({ password }: TLogInFormData) => {
        startTransition(async () => {
            const result = await action.auth.logInWithPassword({ email, password });

            if (!result.success) {
                setError(result);
            } else {
                router.push('/');
            }
        });
    };

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="component-step">
                <div className="text-caption">Chào <strong>{email}</strong>, mời bạn đăng nhập</div>

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

                <FormButtonSubmit className='btn-primary' isPending={isPending}>Đăng nhập</FormButtonSubmit>
                <div className="text-sm text-blue-500 cursor-pointer">Quên mật khẩu?</div>
                <Button variant="ghost" className="btn-second" onClick={onEmailChange}>← Thay đổi email</Button>
            </form>

            <FormError message={error?.message} />
        </Form>
    );
}

export default LogInStep;
