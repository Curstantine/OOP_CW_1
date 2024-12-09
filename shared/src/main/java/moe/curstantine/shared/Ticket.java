package moe.curstantine.shared;

import java.time.LocalDateTime;

public class Ticket {
	final String id;
	final String vendorId;
	final String customerId;
	final LocalDateTime createdAt;
	final LocalDateTime bookedAt;

	public Ticket(String id, String vendorId, String customerId, LocalDateTime createdAt, LocalDateTime bookedAt) {
		this.id = id;
		this.vendorId = vendorId;
		this.customerId = customerId;
		this.createdAt = createdAt;
		this.bookedAt = bookedAt;
	}
}
