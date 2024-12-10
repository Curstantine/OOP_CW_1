package moe.curstantine.backend.service;

import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {
	private final TicketRepository ticketRepository;

	@Autowired
	public TicketService(TicketRepository ticketRepository) throws InterruptedException {
		this.ticketRepository = ticketRepository;

		for (int x = 0; x < 10; x++) {
			final UUID vendorId = UUID.randomUUID();
			final Ticket ticket = new Ticket(vendorId);
			this.ticketRepository.save(ticket);
			System.out.println("Ticket Created " + ticket);
		}
	}
}
