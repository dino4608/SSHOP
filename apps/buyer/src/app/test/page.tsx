

import { api } from "@/lib/api-definition";
import { serverFetch } from "@/lib/fetch/fetch.server";
import { TCategory } from "@/types/product.types";

export default async function Page() {
    const categories: TCategory[] = (await serverFetch(api.category.getTree())).data;
    return (
        <>
            <ul>
                {categories.map((category) => (
                    <li key={category.id}>{category.name}</li>
                ))}
            </ul>
            <br />
            <ul>
                {categories.map((category) => (
                    <li key={category.id}>{category.name}</li>
                ))}
            </ul>
        </>
    )
}