import { Credit } from "./credit";

export interface Client{
    id: number,
    fullName: string,
    email:string,
    passportNumber: number,
    credits: Credit[];
}