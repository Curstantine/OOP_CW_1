package moe.curstantine.backend.repository;

import moe.curstantine.backend.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.chrono.Chronology;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends CrudRepository<Ticket, UUID> {
	@Override
	Optional<Ticket> findById(UUID uuid);

	List<Ticket> findByCustomerId(UUID customerId);

	List<Ticket> findByVendorId(UUID vendorId);
}
