export type TCategory = {
    id: number;
    name: string;
    slug: string;
    photo: string | null;
    description: string;
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
    photo: string;
    position?: number;
};
