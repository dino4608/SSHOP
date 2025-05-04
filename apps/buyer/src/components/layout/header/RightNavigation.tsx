'use client';
import { NavigationMenu, NavigationMenuList } from "@/components/ui/navigation-menu";
import { MessageCircleMore, SquareMenu, UserRound } from "lucide-react";
import AccountDropdown from "./AccountDropdown";
import { NavigationDropdownItem, NavigationLinkItem } from "@/components/ui/custom/navigation-menu";
import useAuth from "@/hooks/useAuth";
import { ReduxProvider } from "@/store/provider";

const RightNavigation = () => {
    const { isAuthenticated, currentUser } = useAuth();

    return (
        <NavigationMenu>
            <NavigationMenuList className="gap-0">
                {isAuthenticated
                    ? (<NavigationDropdownItem dropdown={<AccountDropdown />}>
                        {`Hi ${currentUser?.name}`}
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

const WrappedRightNavigation = () => {
    return (
        <ReduxProvider>
            <RightNavigation />
        </ReduxProvider>
    )
}

export default WrappedRightNavigation;