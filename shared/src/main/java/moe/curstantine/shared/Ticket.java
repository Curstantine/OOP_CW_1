package moe.curstantine.shared;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Ticket {
	final UUID id;
	final UUID vendorId;
	final LocalDateTime createdAt;

	UUID customerId;
	LocalDateTime bookedAt;

	public Ticket(UUID vendorId) {
		this.id = UUID.randomUUID();
		this.vendorId = vendorId;
		this.createdAt = LocalDateTime.now();
	}

	public Ticket(UUID id, UUID vendorId, LocalDateTime createdAt) {
		this.id = id;
		this.vendorId = vendorId;
		this.createdAt = createdAt;
	}

	public boolean isBooked() {
		return customerId != null && bookedAt != null;
	}

	public void book(UUID customerId) {
		this.customerId = customerId;
		this.bookedAt = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Ticket ticket = (Ticket) o;
		return Objects.equals(id, ticket.id) && Objects.equals(vendorId, ticket.vendorId) && Objects.equals(customerId, ticket.customerId) && Objects.equals(createdAt, ticket.createdAt) && Objects.equals(bookedAt, ticket.bookedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, vendorId, customerId, createdAt, bookedAt);
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"id='" + id + '\'' +
				", vendorId='" + vendorId + '\'' +
				", customerId='" + customerId + '\'' +
				", createdAt=" + createdAt +
				", bookedAt=" + bookedAt +
				'}';
	}
}
