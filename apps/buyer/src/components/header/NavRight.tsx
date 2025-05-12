'use client'
import { navigationMenuItemStyle } from '@/components/ui/navigation-menu';
import { useCurrentUser } from "@/hooks/useStore";
import { api } from "@/lib/api-definition";
import { clientFetch } from "@/lib/fetch/fetch.client";
import { cn } from "@/lib/utils";
import { useAppDispatch } from "@/store/hooks";
import { authActions } from "@/store/slices/auth.slice";
import { TUser } from "@/types/identity.types";
import { BadgeCheckIcon, BellIcon, CreditCardIcon, LogOutIcon, MessageCircleMore, SettingsIcon, SquareMenu, UserRound, UserRoundIcon } from "lucide-react";
import { useRouter } from "next/navigation";
import { NavigationLinkItem } from "../ui/custom/navigation-menu";
import { DropdownMenu, DropdownMenuContent, DropdownMenuGroup, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "../ui/dropdown-menu";
import { NavigationMenu, NavigationMenuItem, NavigationMenuList } from "../ui/navigation-menu";
import { toast } from 'sonner';

const NavAccount = ({ currentUser }: { currentUser: TUser }) => {
    const dispatch = useAppDispatch()
    const router = useRouter()

    const onLogout = async () => {
        const res = await clientFetch(api.auth.logout()) // ← gọi API logout

        if (res.success) {
            console.log(">>> NavAccount: Log out: isAuthenticated: ", res.data.isAuthenticated)
            dispatch(authActions.clear())
            router.refresh();
        } else {
            toast.error(res.error)

        }
    }

    return (
        <NavigationMenuItem>
            <DropdownMenu>
                <DropdownMenuTrigger className={navigationMenuItemStyle()} asChild>
                    <div className="group flex flex-col xl:flex-row text-xs xl:text-sm items-center gap-0.5">
                        <UserRoundIcon className="w-5" />
                        <span>{`Hi ${currentUser.name ?? currentUser.email}`}</span>

                    </div>

                </DropdownMenuTrigger>
                <DropdownMenuContent className="w-56">
                    <DropdownMenuLabel>My Account</DropdownMenuLabel>
                    <DropdownMenuSeparator />
                    <DropdownMenuGroup>
                        <DropdownMenuItem>
                            <BadgeCheckIcon color="black" />
                            <span>Profile</span>

                        </DropdownMenuItem>
                        <DropdownMenuItem>
                            <CreditCardIcon color="black" />
                            <span>Billing</span>

                        </DropdownMenuItem>
                        <DropdownMenuItem>
                            <BellIcon color="black" />
                            <span>Notifications</span>

                        </DropdownMenuItem>
                        <DropdownMenuItem>
                            <SettingsIcon color="black" />
                            <span>Settings</span>

                        </DropdownMenuItem>
                    </DropdownMenuGroup>

                    <DropdownMenuSeparator />
                    <DropdownMenuItem onClick={onLogout}>
                        <LogOutIcon className="text-red-500" />
                        <span className="text-red-500">Log out</span>

                    </DropdownMenuItem>
                </DropdownMenuContent>
            </DropdownMenu>
        </NavigationMenuItem>
    )
}

export const NavRight = () => {
    const currentUser = useCurrentUser();

    return (
        <NavigationMenu>
            <NavigationMenuList className="gap-0">
                {currentUser
                    ? (<NavAccount currentUser={currentUser} />)
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