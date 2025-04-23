'use client';
import React, { ReactElement } from 'react';
import Link from 'next/link';
import { NavigationMenuItem, navigationMenuItemStyle, navigationMenuLongItemStyle } from '@/components/ui/navigation-menu';
import { cn } from '@/lib/utils';

type Props = {
    href: string;
    icon?: ReactElement<any>
    iconClassName?: string;
    longStyle?: boolean;
    children: React.ReactNode
}

const NavigationLinkItem = ({ href, icon, children, iconClassName = 'w-5', longStyle = false }: Props) => {
    const styledIcon = icon ? React.cloneElement(icon, { className: iconClassName, }) : null;

    return (
        <NavigationMenuItem>
            <Link href={href}>
                <div className={cn(
                    longStyle
                        ? navigationMenuLongItemStyle()
                        : navigationMenuItemStyle(),
                    "group flex flex-col xl:flex-row text-xs xl:text-sm items-center gap-0.5")}
                >
                    {styledIcon}
                    {children}
                </div>
            </Link>
        </NavigationMenuItem>

    );
};

export default NavigationLinkItem;
