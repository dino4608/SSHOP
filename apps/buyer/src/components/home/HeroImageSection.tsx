'use client';

import React from "react";
import HomeContainer from "./HomeContainer";
import Image from "next/image";

const HeroImageSection = () => {
    return (
        <HomeContainer>
            <div className='
                w-full h-50
                flex justify-center items-center
                relative'
            >
                <Image
                    src='/images/hero.webp'
                    alt='hero'
                    fill
                    className='object-cover rounded-lg'
                />
            </div>
        </HomeContainer>
    );
}

export default HeroImageSection;