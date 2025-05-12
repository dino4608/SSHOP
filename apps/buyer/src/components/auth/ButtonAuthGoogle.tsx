'use client';

import { Button } from '@/components/ui/button';

type Props = {
  children: string;
  className?: string;
};

export default function ButtonAuthGoogle({ children, className }: Props) {

  const onClick = () => {


    const CALLBACK_URL = process.env.NEXT_PUBLIC_REDIRECT_URI;
    const AUTH_URL = process.env.NEXT_PUBLIC_AUTH_URI;
    const CLIENT_ID = process.env.NEXT_PUBLIC_CLIENT_ID;

    const TARGET_URL = `${AUTH_URL}?redirect_uri=${encodeURIComponent(CALLBACK_URL as string)
      }&response_type=code&client_id=${CLIENT_ID
      }&scope=openid%20email%20profile`;

    console.log('>>> ButtonAuthGoogle: TARGET_URL: ', TARGET_URL);
    window.location.href = TARGET_URL;
  };

  return (
    <Button type="button" variant="outline" className={className || ''} onClick={onClick}>
      {children}
    </Button>
  );
}
