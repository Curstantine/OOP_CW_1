import type { GenericResponse } from "./generic";

export interface Configuration {
	/**
	 * Maximum number of tickets the application can store.
	 */
	maxTicketCapacity: number;

	/**
	 * The total number of tickets in the system (?)
	 * <p>
	 * <strong>Note: Do not use this within the system, as it is overlapping with {@link #maxTicketCapacity}.</strong>
	 * However, this property mustn't be removed due to several references within the specification.
	 * <p>
	 * The specification itself is quite unsound, for an example, quoting the specification:
	 * <p>
	 * <ul>
	 * <li>Total Tickets (totalTickets): The total tickets available in the system.</li>
	 * <li>Ticket Release Rate (ticketReleaseRate): How frequently vendors add tickets.</li>
	 * <li>Customer Retrieval Rate (customerRetrievalRate): How often customers purchase tickets.</li>
	 * <li>Max Ticket Capacity (maxTicketCapacity): Maximum capacity of tickets the system can hold.</li>
	 * </ul>
	 */
	totalTickets: number;

	/**
	 * The amount of time it takes to release a ticket.
	 */
	ticketReleaseRate: number;

	/**
	 * The amount of time it takes for a customer to book a ticket.
	 */
	customerRetrievalRate: number;
}

export type ConfigurationResponse = GenericResponse<Configuration>;
