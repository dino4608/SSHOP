'use client';

import React, { ReactElement } from 'react';
import Link from 'next/link';

type Props = {
    href: string;
    icon: ReactElement<any> // React.ReactNode; // ?
    iconClassName?: string;
    children: React.ReactNode
}

const NavLinkItem: React.FC<Props> = ({ href, icon, children, iconClassName = 'w-5' }) => {
    const styledIcon = React.cloneElement(icon, { className: iconClassName, });

    return (
        <Link href={href}>
            <div className="flex flex-col xl:flex-row text-xs xl:text-sm items-center gap-0.5">
                {styledIcon}
                {children}
            </div>
        </Link>
    );
};

export default NavLinkItem;
