'use client';

import { app } from "@/app/utils";
import Link from "next/link";

const Logo: React.FC = () => {
    return (
        <Link
            href={'/'}
            className='text-[var(--dino-red-1)] text-xl sm:text-2xl font-bold tracking-tight'
        >
            {app.name}
        </Link>
    );
};

export default Logo;