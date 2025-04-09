import { api } from '@/lib/api/request';
import User from '@/types/identity-domain.type';


// Gọi API kiểm tra username
type CheckUsernameResponse = {
    exists: boolean;
    hasPassword?: boolean;
};

export const checkUsername = (username: string) => {
    return api.get<CheckUsernameResponse>(`/user/get/username/${username}`, undefined, false);
};