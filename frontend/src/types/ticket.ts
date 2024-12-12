import type { GenericResponse } from "./generic";

export interface Ticket {
	id: string;
	createdAt: string;
	vendorId: string;

	bookedAt: string;
	customerId: string | null;
}

export interface AggregatedTicket {
	id: string | null;
	isCreated: boolean;
	isBooked: boolean;
}

export type TicketCountResponse = GenericResponse<number>;
export type AggregatedTicketCollectionResponse = GenericResponse<AggregatedTicket[]>;
