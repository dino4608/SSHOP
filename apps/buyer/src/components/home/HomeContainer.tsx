import React from "react";

type Props = {
    children: React.ReactNode;
}

const HomeContainer: React.FC<Props> = ({ children }) => {
    return (
        <div className='w-full bg-white px-2 sm:px-10 lg:px-20'>
            <div className='container mx-auto'>
                {children}
            </div>
        </div>
    );
};


export default HomeContainer;