package moe.curstantine.backend.controller;

import moe.curstantine.backend.entity.Vendor;
import moe.curstantine.backend.repository.VendorRepository;
import moe.curstantine.backend.service.VendorPoolService;
import moe.curstantine.shared.GenericResponse;
import moe.curstantine.shared.body.CreateVendor;
import moe.curstantine.shared.exception.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vendor")
public class VendorController {
	private final VendorRepository vendorRepository;
	private final VendorPoolService vendorPoolService;

	public VendorController(VendorRepository vendorRepository, VendorPoolService vendorPoolService) {
		this.vendorRepository = vendorRepository;
		this.vendorPoolService = vendorPoolService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<GenericResponse<Vendor>> getVendorById(@PathVariable UUID id) throws DataNotFoundException {
		return vendorRepository.findById(id)
				.map(GenericResponse::fromSuccess)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new DataNotFoundException("Vendor with id " + id + " not found"));
	}

	@PostMapping
	public ResponseEntity<GenericResponse<Vendor>> createVendor(@RequestBody CreateVendor body) {
		final Vendor payload = new Vendor(body.name());
		final Vendor data = vendorRepository.save(payload);

		vendorPoolService.startRunnable(data);
		return ResponseEntity.ok(GenericResponse.fromSuccess(data));
	}

	@GetMapping("/count")
	public ResponseEntity<GenericResponse<Long>> count() {
		return ResponseEntity.ok(GenericResponse.fromSuccess(vendorRepository.count()));
	}

	@PostMapping("/startAll")
	public ResponseEntity<GenericResponse<Boolean>> startAll() {
		vendorPoolService.resumeSuspended();
		return ResponseEntity.ok(GenericResponse.fromSuccess(true));
	}

	@PostMapping("/endAll")
	public ResponseEntity<GenericResponse<Boolean>> stopAll() {
		vendorPoolService.suspendRunning();
		return ResponseEntity.ok(GenericResponse.fromSuccess(true));
	}
}
