import { GenericResponse } from "./generic";

export interface Vendor {
	id: string;
	name: string;
}

export interface CreateVendor {
	name: string;
}

export type VendorResponse = GenericResponse<Vendor>;
export type VendorCountResponse = GenericResponse<number>;
