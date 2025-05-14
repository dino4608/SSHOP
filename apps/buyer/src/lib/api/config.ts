import { HttpMethod } from "../constants";

export type TApiDefinition<T> = {
    route: string;
    method: HttpMethod;
    query?: object;
    body?: object;
};