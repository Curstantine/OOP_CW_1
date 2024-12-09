package moe.curstantine.shared;

/**
 * Reference {@link Configuration} to pass down to immutable instances
 */
public final class ReferenceConfiguration {
	final int totalTickets;
	final int ticketReleaseRate;
	final int customerRetrievalRate;
	final int maxTicketCapacity;

	public ReferenceConfiguration(Configuration config) {
		this.totalTickets = config.getTotalTickets();
		this.ticketReleaseRate = config.getTicketReleaseRate();
		this.customerRetrievalRate = config.getCustomerRetrievalRate();
		this.maxTicketCapacity = config.getMaxTicketCapacity();
	}
}
