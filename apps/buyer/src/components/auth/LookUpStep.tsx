'use client';

import {
    FormControl,
    FormField, FormItem, FormLabel,
    FormMessage,
    Form as FormUI
} from "@/components/ui/form";
import { Input } from '@/components/ui/input';
import { initActionState } from '@/server-actions/utils';
import { TActionState } from '@/types/base.type';
import { lookUpFormSchema, TLookUpFormData } from '@/validations/auth.validations';
import { zodResolver } from '@hookform/resolvers/zod';
import { useState, useTransition } from 'react';
import { useForm } from 'react-hook-form';
import FormButtonSubmit, { FormError } from '../ui/custom/form';
import ButtonAuthGoogle from './ButtonAuthGoogle';

type TProps = {
    onSubmit: (data: TLookUpFormData) => Promise<TActionState<undefined> | undefined>;
}

const LookUpStep = ({ onSubmit: submit }: TProps) => {
    // TODO:
    // create a custom hook - useFormSubmitToast
    // that associates useForm, useState, useTransition, onSubmit, useToast

    const form = useForm<TLookUpFormData>({
        resolver: zodResolver(lookUpFormSchema),
        defaultValues: { email: '' },
    });

    const [error, setError] = useState(initActionState);

    const [isPending, startTransition] = useTransition();

    const onSubmit = async ({ email }: TLookUpFormData) => {
        startTransition(async () => {
            const result = await submit({ email });

            if (result && !result.success) {
                setError(result);
            }
        });
    };

    return (
        <FormUI {...form}>
            <form
                className="component-step"
                onSubmit={form.handleSubmit(onSubmit)}
            >
                <FormField
                    control={form.control}
                    name="email"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Email</FormLabel>
                            <FormControl>
                                <Input placeholder="Nhập email" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />

                <FormButtonSubmit className='btn-primary' isPending={isPending}>Tiếp tục</FormButtonSubmit>
                <div className="text-or">Hoặc</div>
                <ButtonAuthGoogle className='btn-second' >Tiếp tục với Google</ButtonAuthGoogle>
            </form>

            <FormError message={error?.message} />
        </FormUI >
    );
}

export default LookUpStep;