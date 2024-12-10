package moe.curstantine.backend.models;

import moe.curstantine.shared.Producer;
import moe.curstantine.shared.Ticket;

import java.util.UUID;

public class Vendor implements Producer {
	private final UUID id;
	private final String name;

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
