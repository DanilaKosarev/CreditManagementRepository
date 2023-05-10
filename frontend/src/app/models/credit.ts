import { Payment } from "./payment";

export interface Credit{
    id: number,
    amount:number,
    monthQuantity:number,
    payments: Payment[]   
}