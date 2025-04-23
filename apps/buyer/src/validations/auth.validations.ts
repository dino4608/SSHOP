import { z } from "zod";

// lookUpFormSchema //
export const lookUpFormSchema = z.object({
    email: z.string().email('Email nên đúng định dạng.'),
});

export type TLookUpFormData = z.infer<typeof lookUpFormSchema>;

// logInFormSchema //
export const logInFormSchema = z.object({
    password: z.string().min(6, 'Password nên có ít nhất 6 ký tự.'),
});

export type TLogInFormData = z.infer<typeof logInFormSchema>;