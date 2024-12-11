package moe.curstantine.backend.controller;

import moe.curstantine.backend.entity.Customer;
import moe.curstantine.backend.repository.CustomerRepository;
import moe.curstantine.backend.service.CustomerPoolService;
import moe.curstantine.shared.GenericResponse;
import moe.curstantine.shared.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import moe.curstantine.shared.body.CreateCustomer;


import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	private final CustomerRepository customerRepository;
	private final CustomerPoolService customerPoolService;

	@Autowired
	public CustomerController(CustomerRepository customerRepository, CustomerPoolService customerPoolService) {
		this.customerRepository = customerRepository;
		this.customerPoolService = customerPoolService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<GenericResponse<Customer>> getCustomerById(@PathVariable UUID id) throws DataNotFoundException {
		return customerRepository.findById(id)
				.map(GenericResponse::fromSuccess)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new DataNotFoundException("Customer with id " + id + " not found"));
	}

	@PostMapping
	public ResponseEntity<GenericResponse<Customer>> createCustomer(@RequestBody CreateCustomer body) {
		final Customer payload = new Customer(body.name());
		final Customer data = customerRepository.save(payload);

		customerPoolService.startRunnable(data);
		return ResponseEntity.ok(GenericResponse.fromSuccess(data));
	}

	@PostMapping("/startAll")
	public ResponseEntity<GenericResponse<Boolean>> startAll() {
		customerPoolService.resumeSuspended();
		return ResponseEntity.ok(GenericResponse.fromSuccess(true));
	}

	@PostMapping("/stopAll")
	public ResponseEntity<GenericResponse<Boolean>> stopAll() {
		customerPoolService.suspendRunning();
		return ResponseEntity.ok(GenericResponse.fromSuccess(true));
	}
}
