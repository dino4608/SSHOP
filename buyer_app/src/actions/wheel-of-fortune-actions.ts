"use server";

import { getProductById, Product, searchProducts } from "@/server/product/api";

export const getWheelOfFortuneConfiguration = async () => {
    // const client = createClient({
    //     projectId: process.env.NEXT_PUBLIC_SANITY_PROJECT_ID,
    //     dataset: process.env.NEXT_PUBLIC_SANITY_DATASET,
    //     apiVersion: process.env.NEXT_PUBLIC_SANITY_API_VERSION,
    //     useCdn: true,
    // });

    const randomProducts = await searchProducts(''); // todo: replace with actual API call to fetch random products

    const today = new Date();
    const day = today.getDate();
    const month = today.getMonth();
    const year = today.getFullYear();

    const winningIndex = (day * 31 + month * 12 + year) % randomProducts.length;

    return {
        randomProducts,
        winningIndex,
    }
}