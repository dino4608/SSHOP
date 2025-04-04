'use server';

import { getAllProductCategories } from "@/server/product-category/api";
import Link from "next/link";


const HeaderCategorySelector = async () => {
    const productCategories = await getAllProductCategories();

    return (
        <div className='relative inline-block'>
            <button className='peer group text-gray-700 hover:text-gray-900 text-sm font-medium flex items-center gap-1'>
                Categories
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="16"
                    height="16"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="transition-transform duration-200 group-hover:rotate-180"
                >
                    <path d="m6 9 6 6 6-6" />
                </svg>
            </button>

            {/* todo: fix the overflow-auto not working, categories expanding vertically*/}
            <div className='absolute top-full left-0 pt-2 opacity-0 invisible peer-hover:opacity-100 peer-hover:visible hover:opacity-100 hover:visible transition-all duration-300 ease-in-out'>
                <div className='w-64 bg-white rounded-lg shadow-xl border border-gray-100 overflow-hidden'>
                    <div className='py-2'>
                        {productCategories.map((productCategory) => (
                            <Link
                                key={productCategory.id}
                                href={`/product-category/${productCategory?.slug}`}
                                className='block px-4 py-3 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 transition-colors duration-100'
                                prefetch
                            >
                                {productCategory.title}
                            </Link>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HeaderCategorySelector;