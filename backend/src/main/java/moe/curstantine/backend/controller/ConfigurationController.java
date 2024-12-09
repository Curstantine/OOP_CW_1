package moe.curstantine.backend.controller;

import moe.curstantine.backend.service.ConfigurationService;
import moe.curstantine.shared.GenericResponse;
import moe.curstantine.shared.ReferenceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/config")
public class ConfigurationController {
	private final ConfigurationService configService;

	@Autowired
	public ConfigurationController(ConfigurationService configService) {
		this.configService = configService;
	}

	@GetMapping
	public ResponseEntity<GenericResponse<ReferenceConfiguration>> get() {
		final ReferenceConfiguration data = configService.getRef();
		final GenericResponse<ReferenceConfiguration> resp = GenericResponse.fromSuccess(data);

		return ResponseEntity.ok(resp);
	}

	@PatchMapping
	public ResponseEntity<GenericResponse<ReferenceConfiguration>> update(@RequestBody Map<String, Object> updates) throws IOException, IllegalArgumentException {
		for (Map.Entry<String, Object> entry : updates.entrySet()) {
			final String value = entry.getValue().toString();

			switch (entry.getKey()) {
				case "totalTickets":
					configService.setTotalTickets(Integer.parseInt(value));
					break;
				case "ticketReleaseRate":
					configService.setTicketReleaseRate(Integer.parseInt(value));
					break;
				case "customerRetrievalRate":
					configService.setCustomerRetrievalRate(Integer.parseInt(value));
					break;
				case "maxTicketCapacity":
					configService.setMaxTicketCapacity(Integer.parseInt(value));
					break;
				default:
					throw new IllegalArgumentException("Invalid key: " + entry.getKey());
			}
		}

		configService.save();

		final ReferenceConfiguration data = configService.getRef();
		final GenericResponse<ReferenceConfiguration> resp = GenericResponse.fromSuccess(data);

		return ResponseEntity.ok(resp);
	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public <T> ResponseEntity<GenericResponse<T>> handleIOException(IOException e) {
		final GenericResponse<T> resp = GenericResponse.fromError(e.getMessage());
		return ResponseEntity.internalServerError().body(resp);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public <T> ResponseEntity<GenericResponse<T>> handleIllegalArgumentException(IllegalArgumentException e) {
		final GenericResponse<T> resp = GenericResponse.fromError("Malformed request: " + e.getMessage());
		return ResponseEntity.badRequest().body(resp);
	}
}
