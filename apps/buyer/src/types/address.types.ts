export type TAddress = {
    id: number;
    contactName: string;
    contactPhone: string;
    province: string;
    district: string;
    commune: string;
    street: string;
    isDefault: boolean;
    createdAt: Date;
    updatedAt: Date;
    isDeleted: boolean;
};