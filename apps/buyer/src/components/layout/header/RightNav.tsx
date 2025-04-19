'use client';

import { MessageCircleMore, SquareMenu, UserRound } from "lucide-react";
import NavGroup from "./NavGroup";
import NavLinkItem from "./NavLinkItem";

const RightNav: React.FC = () => {
    return (
        <NavGroup>
            {/* Sign up / in */}
            <div className='flex items-center gap-0.5'>
                <UserRound className='w-5' />
                Sign up / in
            </div>

            <NavLinkItem href='/inbox' icon={<MessageCircleMore />}>
                Inbox
            </NavLinkItem>

            <NavLinkItem href='/coupons' icon={<SquareMenu />}>
                Orders
            </NavLinkItem>
        </NavGroup>
    );
};

export default RightNav;