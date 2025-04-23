
import { api } from "@/services";
import { Category } from "@/types/product.types"

export default async function Page() {
    const categories: Category[] = (await api.category.getTree()).data;
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