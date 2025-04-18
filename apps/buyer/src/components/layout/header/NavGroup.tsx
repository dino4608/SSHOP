'use client';

type Props = {
    children: React.ReactNode
}

const NavGroup: React.FC<Props> = ({ children }) => {
    return (
        <div className='flex items-center gap-1 xl:gap-4 text-sm' >
            {children}
        </div>
    );
};

export default NavGroup;