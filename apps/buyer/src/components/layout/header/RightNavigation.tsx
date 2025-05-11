'use client';
import { NavigationMenu, NavigationMenuList } from "@/components/ui/navigation-menu";
import { MessageCircleMore, SquareMenu, UserRound } from "lucide-react";
import AccountDropdown from "./AccountDropdown";
import { NavigationDropdownItem, NavigationLinkItem } from "@/components/ui/custom/navigation-menu";
import { useCurrentUser } from "@/hooks/useRootState";

const RightNavigation = () => {
    const currentUser = useCurrentUser();

    return (
        <NavigationMenu>
            <NavigationMenuList className="gap-0">
                {currentUser
                    ? (<NavigationDropdownItem dropdown={<AccountDropdown />}>
                        {`Hi ${currentUser.name ?? currentUser.email}`}
                    </NavigationDropdownItem>)
                    : (<NavigationLinkItem href='/auth' icon={<UserRound />}>
                        Sign up / in
                    </NavigationLinkItem>)}

                <NavigationLinkItem href='/inbox' icon={<MessageCircleMore />}>
                    Inbox
                </NavigationLinkItem>

                <NavigationLinkItem href='/orders' icon={<SquareMenu />}>
                    Orders
                </NavigationLinkItem>
            </NavigationMenuList>
        </NavigationMenu >
    );
};

export default RightNavigation;