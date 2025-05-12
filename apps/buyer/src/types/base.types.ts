/*
 * NOTE:
 * Vì sao KHÔNG nên nhập types vào services?
 * - types nên là tầng thấp, không phụ thuộc ngược vào tầng cao như services (nguyên tắc dependency).
 * - Nếu bạn cho phép types → service, sau này dễ bị vòng phụ thuộc (circular dependencies) hoặc import sai chiều.
 * - Khi tách, services dùng types, nhưng types không biết gì về service — đúng hướng phụ thuộc.
 */

export type TApiResponse<T> = {
    success: boolean;
    status: number;
    code: number;
    error: string;
    data: T;
}