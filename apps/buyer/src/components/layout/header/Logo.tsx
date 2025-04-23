import { APP } from "@/app/layout";
import Link from "next/link";

const Logo = () => {
    return (
        <Link
            href={'/'}
            className='text-[var(--dino-red-1)] text-xl sm:text-2xl font-bold tracking-tight'
        >
            {APP.name}
        </Link>
    );
};

export default Logo;