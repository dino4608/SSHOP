'use client';

import {
    FormControl,
    FormField, FormItem, FormLabel,
    FormMessage,
    Form as FormUI
} from "@/components/ui/form";
import { Input } from '@/components/ui/input';
import { TApiResponse } from '@/types/base.types';
import { TLookupIdentifierResponse } from "@/types/auth.types";
import { lookUpFormSchema, TLookUpFormData } from '@/validations/auth.validations';
import { zodResolver } from '@hookform/resolvers/zod';
import { useState, useTransition } from 'react';
import { useForm } from 'react-hook-form';
import FormButtonSubmit, { FormError } from '../ui/custom/form';
import ButtonAuthGoogle from './ButtonAuthGoogle';

type TProps = {
    lookupIdentifier: (data: TLookUpFormData) => Promise<TApiResponse<TLookupIdentifierResponse> | undefined>;
}

const LookUpStep = ({ lookupIdentifier }: TProps) => {
    // TODO:
    // create a custom hook - useFormSubmitToast
    // that associates useForm, useState, useTransition, onSubmit, useToast

    const form = useForm<TLookUpFormData>({
        resolver: zodResolver(lookUpFormSchema),
        defaultValues: { email: '' },
    });
    const [error, setError] = useState('');
    const [isPending, startTransition] = useTransition();

    const onSubmit = async ({ email }: TLookUpFormData) => {
        startTransition(async () => {
            const result = await lookupIdentifier({ email });

            if (result && !result.error) {
                setError(result.error);
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

            <FormError message={error} />
        </FormUI >
    );
}

export default LookUpStep;