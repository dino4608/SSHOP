'use client';

import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import {
    Form, FormField, FormItem, FormLabel, FormControl, FormMessage
} from "@/components/ui/form";
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';

const schema = z.object({
    username: z.string().min(3, 'Username phải có ít nhất 3 ký tự'),
});

type FormData = z.infer<typeof schema>;

export default function UsernameStep({ onSubmit }: { onSubmit: (username: string) => void }) {
    const form = useForm<FormData>({ resolver: zodResolver(schema), defaultValues: { username: '' } });

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit((data) => onSubmit(data.username))} className="space-y-4">
                <FormField
                    control={form.control}
                    name="username"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Username</FormLabel>
                            <FormControl>
                                <Input placeholder="Nhập username" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <Button type="submit" className="w-full">Continue</Button>
                <div className="text-center text-sm text-gray-500">Hoặc đăng nhập bằng Google</div>
                <Button variant="outline" className="w-full">Continue with Google</Button>
            </form>
        </Form>
    );
}
