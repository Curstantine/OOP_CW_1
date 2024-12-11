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

export type TicketResponse = GenericResponse<Ticket>;
export type TicketCollectionResponse = GenericResponse<Ticket[]>;
export type AggregatedTicketCollectionResponse = GenericResponse<AggregatedTicket[]>;
