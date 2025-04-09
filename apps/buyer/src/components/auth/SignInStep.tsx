'use client';

import {
    Form, FormField, FormItem, FormLabel, FormControl, FormMessage
} from "@/components/ui/form";
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';

const schema = z.object({
    password: z.string().min(6, 'Mật khẩu tối thiểu 6 ký tự'),
});

type FormData = z.infer<typeof schema>;

type Props = {
    username: string;
    onUsernameChange: () => void;
};

export default function SignInStep({ username, onUsernameChange }: Props) {
    const form = useForm<FormData>({
        resolver: zodResolver(schema),
        defaultValues: {
            password: '',
        },
    });

    const onSubmit = (data: FormData) => {
        console.log('Sign in with:', username, data.password);
        // Gọi API sign in
    };

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                <div className="text-sm">Chào <strong>{username}</strong>, mời bạn đăng nhập</div>

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

                <Button type="submit" className="w-full">Sign in</Button>
                <div className="text-sm text-blue-500 cursor-pointer">Quên mật khẩu?</div>
                <Button variant="ghost" className="w-full" onClick={onUsernameChange}>← Thay đổi username</Button>
            </form>
        </Form>
    );
}
