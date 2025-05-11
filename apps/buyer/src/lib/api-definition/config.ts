import { HttpMethod } from "../constants";

export type TApiDefinition = {
    route: string;
    method: HttpMethod;
    query?: object;
    body?: object;
};