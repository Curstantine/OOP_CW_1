package moe.curstantine.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import moe.curstantine.backend.interfaces.Consumer;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Customer implements Consumer {
	@Id
	private UUID id;
	private String name;

	protected Customer() {
	}

	public Customer(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}

	public Customer(UUID id, String name) {
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
	public void bookTicket(Ticket ticket) {
		ticket.book(id);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
