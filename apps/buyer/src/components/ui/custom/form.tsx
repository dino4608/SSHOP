import React from 'react';
import { Button } from '@/components/ui/button';
import { Loader2 } from 'lucide-react';

type FormErrorProps = {
    message?: string;
}

export const FormError = ({ message }: FormErrorProps) => {
    if (!(message && message.length > 0))
        return null;

    return (
        <p className="text-center text-sm text-red-600">{message}</p>
    );
};

type FormButtonSubmitProps = {
    isPending: boolean;
    children: React.ReactNode;
    className?: string;
}

export default function FormButtonSubmit({ isPending, children, className }: FormButtonSubmitProps) {
    return (
        <Button
            type="submit"
            className={`flex items-center justify-center gap-2 ${isPending ? 'cursor-not-allowed' : ''} ${className ?? ''}`}
            disabled={isPending}
        >
            {isPending ? (
                <>
                    <Loader2 className="h-4 w-4 animate-spin" />
                    Đang {children}...
                </>
            ) : (
                children
            )}
        </Button>
    );
}

