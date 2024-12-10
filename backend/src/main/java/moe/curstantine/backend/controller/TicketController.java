package moe.curstantine.backend.controller;

import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {
	private final TicketRepository ticketRepository;

	@Autowired
	public TicketController(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable UUID id) {
		return ticketRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<Iterable<Ticket>> getAllTickets() {
		Iterable<Ticket> tickets = ticketRepository.findAll();
		return ResponseEntity.ok(tickets);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<Ticket>> getTicketsByCustomerId(@PathVariable UUID customerId) {
		List<Ticket> tickets = ticketRepository.findByCustomerId(customerId);
		return ResponseEntity.ok(tickets);
	}

	@GetMapping("/vendor/{vendorId}")
	public ResponseEntity<List<Ticket>> getTicketsByVendorId(@PathVariable UUID vendorId) {
		List<Ticket> tickets = ticketRepository.findByVendorId(vendorId);
		return ResponseEntity.ok(tickets);
	}
}
