package moe.curstantine.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Ticket {
	@Id
	private UUID id;
	private UUID vendorId;
	private LocalDateTime createdAt;

	private UUID customerId;
	private LocalDateTime bookedAt;

	public Ticket() {
	}

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

	public UUID getId() {
		return id;
	}

	public UUID getVendorId() {
		return vendorId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public LocalDateTime getBookedAt() {
		return bookedAt;
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
