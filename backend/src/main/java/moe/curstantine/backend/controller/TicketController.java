package moe.curstantine.backend.controller;

import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.repository.TicketRepository;
import moe.curstantine.shared.GenericResponse;
import moe.curstantine.shared.exception.DataNotFoundException;
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
	public ResponseEntity<GenericResponse<Ticket>> getTicketById(@PathVariable UUID id) throws DataNotFoundException {
		return ticketRepository.findById(id)
				.map(GenericResponse::fromSuccess)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new DataNotFoundException("Ticket with id " + id + " not found"));
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
		Long count = ticketRepository.count();
		return ResponseEntity.ok(GenericResponse.fromSuccess(count));
	}
}
