'use client';

import {
    Form, FormField, FormItem, FormLabel, FormControl, FormMessage
} from "@/components/ui/form";
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { z } from 'zod';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';

const schema = z.object({
    password: z.string().min(6, 'Mật khẩu tối thiểu 6 ký tự'),
});

type FormData = z.infer<typeof schema>;

type Props = {
    username: string;
    onUsernameChange: () => void;
};

export default function SignUpStep({ username, onUsernameChange }: Props) {
    const form = useForm<FormData>({
        resolver: zodResolver(schema),
        defaultValues: {
            password: '',
        },
    });

    const onSubmit = (data: FormData) => {
        console.log('Sign up:', username, data.password);
        // Gọi API tạo user mới
    };

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                <div className="text-sm">Không tìm thấy tài khoản <strong>{username}</strong>, tạo mới?</div>

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

                <Button type="submit" className="w-full">Sign up</Button>
                <div className="text-center text-sm text-gray-500">Hoặc đăng ký bằng Google</div>
                <Button variant="outline" className="w-full">Sign up with Google</Button>
                <Button variant="ghost" className="w-full" onClick={onUsernameChange}>← Thay đổi username</Button>
            </form>
        </Form>
    );
}
