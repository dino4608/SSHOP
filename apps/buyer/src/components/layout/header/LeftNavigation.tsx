import { NavigationMenu, NavigationMenuList } from "@/components/ui/navigation-menu";
import { Gem, Ticket, Zap } from "lucide-react";
import CategoryDropdown from "./CategoryDropdown";
import React from "react";
import { NavigationDropdownItem, NavigationLinkItem } from "@/components/ui/custom/navigation-menu";

const LeftNavigation = () => {
    return (
        <NavigationMenu>
            <NavigationMenuList className="gap-0">
                <NavigationLinkItem href='/flash-sale' icon={<Zap />}>
                    Flash sale
                </NavigationLinkItem>

                <NavigationLinkItem href='/mall' icon={<Gem />}>
                    Mall
                </NavigationLinkItem>

                <NavigationLinkItem href='/coupons' icon={<Ticket />} iconClassName='w-5 -rotate-45'>
                    Coupons
                </NavigationLinkItem>

                <NavigationDropdownItem dropdown={<CategoryDropdown />} >
                    Categories
                </NavigationDropdownItem>
            </NavigationMenuList>
        </NavigationMenu >
    );
};

export default LeftNavigation;