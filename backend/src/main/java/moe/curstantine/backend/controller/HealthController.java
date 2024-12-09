package moe.curstantine.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

	@GetMapping("/health")
	public Map<String, String> healthCheck() {
		Map<String, String> health = new HashMap<>();
		health.put("status", "UP");
		health.put("message", "Application is healthy and running");
		return health;
	}
}
