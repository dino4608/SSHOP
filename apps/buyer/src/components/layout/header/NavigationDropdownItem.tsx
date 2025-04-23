import { NavigationMenuContent, NavigationMenuItem, NavigationMenuTrigger } from '@/components/ui/navigation-menu';
import React, { ReactElement } from 'react';

type Props = {
    dropdown: ReactElement;
    children: React.ReactNode;
}

const NavigationDropdownItem = ({ dropdown, children }: Props) => {
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
