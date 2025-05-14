export type TProduct = {
    id: number;
    name: string;
    price: number;
    category: string;
};

export type TCategory = {
    id: number;
    name: string;
    description?: string;
    slug: string;
    image: string;
    position?: number;
};