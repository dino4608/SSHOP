import { ListItem } from "@/components/ui/navigation-menu";
import { api } from "@/services";
import React from "react";

const CategoryDropdown = async () => {
    const categories = (await api.category.getTree()).data;

    return (
        <ul className="w-[400px] md:w-[500px] lg:w-[800px] grid md:grid-cols-6 gap-2 p-4">
            {categories.map((item) => (
                <ListItem
                    key={item.id}
                    title={item.name}
                    href={`/category/${item.slug}`}
                >
                    {item.description}
                </ListItem>
            ))}
        </ul>
    );
}

export default CategoryDropdown;