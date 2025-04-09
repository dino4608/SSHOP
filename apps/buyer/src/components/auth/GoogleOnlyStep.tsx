'use client';

import { Button } from "@/components/ui/button";

type Props = {
    username: string;
    onUsernameChange: () => void;
};

export default function GoogleOnlyStep({ username, onUsernameChange }: Props) {
    return (
        <div className="space-y-4 text-center">
            <div className="text-sm">
                Tài khoản <strong>{username}</strong> chưa có mật khẩu. Vui lòng đăng nhập bằng Google.
            </div>
            <Button variant="outline" className="w-full">Continue with Google</Button>
            <Button variant="ghost" className="w-full" onClick={onUsernameChange}>← Thay đổi username</Button>
        </div>
    );
}
