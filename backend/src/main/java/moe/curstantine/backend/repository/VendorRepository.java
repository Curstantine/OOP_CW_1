package moe.curstantine.backend.repository;

import moe.curstantine.backend.entity.Vendor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VendorRepository extends CrudRepository<Vendor, UUID> {
}
