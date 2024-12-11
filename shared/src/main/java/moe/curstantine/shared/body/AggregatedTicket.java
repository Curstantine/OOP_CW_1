package moe.curstantine.shared.body;

import moe.curstantine.shared.Configuration;

import java.util.UUID;

/**
 * Aggregated ticket representation.
 * <p>
 * This model might not contain a valid ticket due to how the aggregation method return on the {@link Configuration#getMaxTicketCapacity()}
 * (flagged by {@link #isCreated})
 *
 * @param id        UUID of the ticket (nullable)
 * @param isCreated Marks whether the ticket exists.
 * @param isBooked  Marks whether the ticket has been booked.
 */
public record AggregatedTicket(UUID id, boolean isCreated, boolean isBooked) {
}
