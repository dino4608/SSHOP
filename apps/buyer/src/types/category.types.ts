export type TCategory = {
    id: string;
    name: string;
    slug: string;
    description: string;
    image: string | null;
    position: number;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
}

export type TCategoryItem = {
    id: number;
    name: string;
    description?: string;
    slug: string;
    image: string;
    position?: number;
};
