package moe.curstantine.backend.controller;

import moe.curstantine.backend.entity.Vendor;
import moe.curstantine.backend.repository.VendorRepository;
import moe.curstantine.backend.service.VendorPoolService;
import moe.curstantine.shared.GenericResponse;
import moe.curstantine.shared.body.CreateVendor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class VendorController {
	private final VendorRepository vendorRepository;
	private final VendorPoolService vendorPoolService;

	public VendorController(VendorRepository vendorRepository, VendorPoolService vendorPoolService) {
		this.vendorRepository = vendorRepository;
		this.vendorPoolService = vendorPoolService;
	}


	@GetMapping("/{id}")
	public ResponseEntity<GenericResponse<Vendor>> getVendorById(@PathVariable UUID id) {
		return vendorRepository.findById(id)
				.map(GenericResponse::fromSuccess)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<GenericResponse<Vendor>> createVendor(@RequestBody CreateVendor body) {
		final Vendor payload = new Vendor(body.name());
		final Vendor data = vendorRepository.save(payload);

		vendorPoolService.startRunnable(data);
		return ResponseEntity.ok(GenericResponse.fromSuccess(data));
	}
}
