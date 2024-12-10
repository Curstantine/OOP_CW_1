package moe.curstantine.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import moe.curstantine.backend.interfaces.Producer;

import java.util.UUID;

@Entity
public class Vendor implements Producer {
	@Id
	private UUID id;
	private String name;

	public Vendor() {
	}

	public Vendor(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}

	public Vendor(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public Ticket createTicket() {
		return new Ticket(id);
	}
}
