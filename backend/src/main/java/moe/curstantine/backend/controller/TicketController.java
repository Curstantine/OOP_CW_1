package moe.curstantine.backend.controller;

import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.repository.TicketRepository;
import moe.curstantine.backend.service.ConfigurationService;
import moe.curstantine.shared.GenericResponse;
import moe.curstantine.shared.body.AggregatedTicket;
import moe.curstantine.shared.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {
	private final TicketRepository ticketRepository;
	private final ConfigurationService configurationService;

	@Autowired
	public TicketController(TicketRepository ticketRepository, ConfigurationService configurationService) {
		this.ticketRepository = ticketRepository;
		this.configurationService = configurationService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<GenericResponse<Ticket>> getTicketById(@PathVariable UUID id) throws DataNotFoundException {
		return ticketRepository.findById(id)
				.map(GenericResponse::fromSuccess)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new DataNotFoundException("Ticket with id " + id + " not found"));
	}

	@GetMapping
	public ResponseEntity<GenericResponse<List<Ticket>>> getAllTickets() {
		List<Ticket> tickets = new ArrayList<>();
		ticketRepository.findAll().forEach(tickets::add);
		return ResponseEntity.ok(GenericResponse.fromSuccess(tickets));
	}

	@GetMapping("/aggregate")
	public ResponseEntity<GenericResponse<List<AggregatedTicket>>> getAllAggregatedTickets() {
		final int maxTicketCap = configurationService.getMaxTicketCapacity();
		final long count = Math.max(ticketRepository.count(), maxTicketCap);
		final Iterator<Ticket> validTickets = ticketRepository.findAll().iterator();

		List<AggregatedTicket> tickets = new ArrayList<>((int) count);

		for (int i = 0; i < count; i++) {
			if (validTickets.hasNext()) {
				Ticket ticket = validTickets.next();
				tickets.add(new AggregatedTicket(ticket.getId(), true, ticket.isBooked()));
				continue;
			}

			tickets.add(new AggregatedTicket(null, false, false));
		}

		return ResponseEntity.ok(GenericResponse.fromSuccess(tickets));
	}


	@GetMapping("/customer/{customerId}")
	public ResponseEntity<GenericResponse<List<Ticket>>> getTicketsByCustomerId(@PathVariable UUID customerId) {
		List<Ticket> tickets = ticketRepository.findByCustomerId(customerId);
		return ResponseEntity.ok(GenericResponse.fromSuccess(tickets));
	}

	@GetMapping("/vendor/{vendorId}")
	public ResponseEntity<GenericResponse<List<Ticket>>> getTicketsByVendorId(@PathVariable UUID vendorId) {
		List<Ticket> tickets = ticketRepository.findByVendorId(vendorId);
		return ResponseEntity.ok(GenericResponse.fromSuccess(tickets));
	}

	@GetMapping("/count")
	public ResponseEntity<GenericResponse<Long>> countTickets() {
		return ResponseEntity.ok(GenericResponse.fromSuccess(ticketRepository.count()));
	}
}
