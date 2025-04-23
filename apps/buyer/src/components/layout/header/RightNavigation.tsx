'use client';
import { NavigationMenu, NavigationMenuList } from "@/components/ui/navigation-menu";
import { MessageCircleMore, SquareMenu, UserRound } from "lucide-react";
import NavigationLinkItem from "./NavigationLinkItem";
import { useState } from "react";
import NavigationDropdownItem from "./NavigationDropdownItem";
import AccountDropdown from "./AccountDropdown";

const accountMenu: { title: string; href: string; description: string }[] = [
    {
        title: "Alert Dialog",
        href: "/docs/primitives/alert-dialog",
        description:
            "A modal dialog that interrupts the user with important content and expects a response.",
    },
    {
        title: "Hover Card",
        href: "/docs/primitives/hover-card",
        description:
            "For sighted users to preview content available behind a link.",
    },
    {
        title: "Progress",
        href: "/docs/primitives/progress",
        description:
            "Displays an indicator showing the completion progress of a task, typically displayed as a progress bar.",
    },
    {
        title: "Scroll-area",
        href: "/docs/primitives/scroll-area",
        description: "Visually or semantically separates content.",
    },
    {
        title: "Tabs",
        href: "/docs/primitives/tabs",
        description:
            "A set of layered sections of content—known as tab panels—that are displayed one at a time.",
    },
    {
        title: "Tooltip",
        href: "/docs/primitives/tooltip",
        description:
            "A popup that displays information related to an element when the element receives keyboard focus or the mouse hovers over it.",
    },
]

const RightNavigation = () => {
    const [isAuth, setIsAuth] = useState(false);

    return (
        <NavigationMenu>
            <NavigationMenuList className="gap-0">
                {isAuth
                    ? (<NavigationDropdownItem dropdown={<AccountDropdown />}>
                        Account
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