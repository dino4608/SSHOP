'use client';

import { Button } from "@/components/ui/button";
import ButtonAuthGoogle from "./ButtonAuthGoogle";

type Props = {
    email: string;
    onEmailChange: () => void;
};

export default function GoogleOnlyStep({ email, onEmailChange }: Props) {

    return (
        <div className="component-step">
            <div className="text-sm">
                Tài khoản <strong>{email}</strong> chưa có mật khẩu. Vui lòng đăng nhập bằng Google.
            </div>
            <ButtonAuthGoogle className='btn-primary' >Đăng nhập với Google</ButtonAuthGoogle>
            <Button variant="ghost" className='btn-second' onClick={onEmailChange}>← Thay đổi email</Button>
        </div>
    );
}
