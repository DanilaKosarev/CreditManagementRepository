import { Credit } from "./credit"

export interface CreditOffer{
    id:number,
    creditLimit: number,
    percent: number
    credits: Credit[]
}