package moe.curstantine.backend.service;

import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketPoolService {
	private final TicketRepository ticketRepository;
	private final ConfigurationService configurationService;

	@Autowired
	public TicketPoolService(TicketRepository ticketRepository, ConfigurationService configurationService) {
		this.ticketRepository = ticketRepository;
		this.configurationService = configurationService;
	}

	/**
	 * Adds a ticket to the ticket pool, and saves it the persistent h2 database.
	 *
	 * @throws ArrayIndexOutOfBoundsException thrown when the amount of tickets is equal to {@link ConfigurationService#getMaxTicketCapacity()}.
	 */
	public synchronized void addTicket(Ticket ticket) throws ArrayIndexOutOfBoundsException {
		final int maxCapacity = configurationService.getMaxTicketCapacity();
		if (getTicketCount() > maxCapacity) throw new ArrayIndexOutOfBoundsException();

		ticketRepository.save(ticket);
	}

	/**
	 * Updates the ticket with corresponding booking data and update the persistent h2 database.
	 *
	 * @throws ArrayIndexOutOfBoundsException thrown when the amount of tickets is equal to zero.
	 * @throws IllegalStateException          thrown when the ticket is already booked.
	 */
	public synchronized void bookTicket(Ticket ticket, UUID customerId) throws ArrayIndexOutOfBoundsException, IllegalStateException {
		if (getTicketCount() == 0) throw new ArrayIndexOutOfBoundsException();
		if (ticket.isBooked()) throw new IllegalStateException("Ticket is already booked");

		ticket.book(customerId);
		ticketRepository.save(ticket);
	}

	/**
	 * Updates the ticket with corresponding booking data and update the persistent h2 database.
	 *
	 * @throws ArrayIndexOutOfBoundsException thrown when the amount of tickets is equal to zero.
	 * @throws IllegalStateException          thrown when the ticket is already booked.
	 * @throws IllegalAccessError             thrown when the ticket by id does not exist.
	 */
	public synchronized void bookTicket(UUID ticketId, UUID customerId) throws ArrayIndexOutOfBoundsException, IllegalStateException, IllegalAccessError {
		if (getTicketCount() == 0) throw new ArrayIndexOutOfBoundsException();

		final Optional<Ticket> optTicket = ticketRepository.findById(ticketId);
		if (optTicket.isEmpty()) throw new IllegalAccessError("Ticket not found");

		final Ticket ticket = optTicket.get();
		if (ticket.isBooked()) throw new IllegalStateException("Ticket is already booked");

		ticket.book(customerId);
		ticketRepository.save(ticket);
	}

	public synchronized void bookLatestTicket(UUID customerId) throws ArrayIndexOutOfBoundsException, IllegalStateException {
		if (getTicketCount() == 0) throw new ArrayIndexOutOfBoundsException();
//		final List<Ticket> latest = ticketRepository.findTicketByCreatedAtNearAndBookedIsEmpty(LocalDateTime.now(), true);
//
//		if (latest.isEmpty()) throw new ArrayIndexOutOfBoundsException();
//		final Ticket ticket = latest.get(0);
	}

	public long getTicketCount() {
		return ticketRepository.count();
	}
}
