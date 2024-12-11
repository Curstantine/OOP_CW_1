import { GenericResponse } from "./generic";

export interface Customer {
	id: string;
	name: string;
}

export interface CreateCustomer {
	name: string;
}

export type CustomerResponse = GenericResponse<Customer>;
export type CustomerCountResponse = GenericResponse<number>;
