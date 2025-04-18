'use client';

import { ChevronDown, Gem, Ticket, Zap } from "lucide-react";
import NavGroup from "./NavGroup";
import NavLinkItem from "./NavLinkItem";

const LeftNav: React.FC = () => {
    return (
        <NavGroup>
            <NavLinkItem href='/flash-sale' icon={<Zap />}>
                Flash sale
            </NavLinkItem>

            <NavLinkItem href='/mall' icon={<Gem />}>
                Mall
            </NavLinkItem>

            <NavLinkItem href='/coupons' icon={<Ticket />} iconClassName='w-5 -rotate-45'>
                Coupons
            </NavLinkItem>

            {/* Category */}
            <div className='flex items-center'>
                Category
                <ChevronDown className='w-5' />
            </div>
        </NavGroup>
    );
};

export default LeftNav;