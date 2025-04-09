'use client';

import { NavigationMenuContent, NavigationMenuItem, NavigationMenuTrigger } from '@/components/ui/navigation-menu';
import React, { ReactElement } from 'react';

type Props = {
    dropdown: ReactElement;
    children: React.ReactNode;
}

const NavigationDropdownItem: React.FC<Props> = ({ dropdown, children }) => {
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

export default NavigationDropdownItem;
