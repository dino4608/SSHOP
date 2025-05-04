'use client';
import { NavigationMenuItem, navigationMenuItemStyle, navigationMenuLongItemStyle } from '@/components/ui/navigation-menu';
import { cn } from '@/lib/utils';
import Link from 'next/link';
import React, { ReactElement } from 'react';

type NavigationLinkItemProps = {
    href: string;
    icon?: ReactElement<any>
    iconClassName?: string;
    longStyle?: boolean;
    children: React.ReactNode
}

export const NavigationLinkItem = ({
    href, icon, children, iconClassName = 'w-5', longStyle = false
}: NavigationLinkItemProps) => {

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

import { NavigationMenuContent, NavigationMenuTrigger } from '@/components/ui/navigation-menu';

type NavigationDropdownItemProps = {
    dropdown: ReactElement;
    children: React.ReactNode;
}

export const NavigationDropdownItem = ({ dropdown, children }: NavigationDropdownItemProps) => {

    return (
        <NavigationMenuItem>
            <NavigationMenuTrigger>
                {children}
            </NavigationMenuTrigger>
            <NavigationMenuContent>
                {dropdown}
            </NavigationMenuContent>
        </NavigationMenuItem>
    );
};



