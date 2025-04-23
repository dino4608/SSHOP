export type Product = {
    id: number;
    name: string;
    price: number;
    category: string;
};

export type Category = {
    id: number;
    name: string;
    description?: string;
    slug: string;
    image: string;
    position?: number;
};