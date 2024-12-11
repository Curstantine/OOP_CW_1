package moe.curstantine.backend.config;

import moe.curstantine.shared.GenericResponse;
import moe.curstantine.shared.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandlerConfig {
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

	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public <T> ResponseEntity<GenericResponse<T>> handleDataNotFoundException(DataNotFoundException e) {
		final GenericResponse<T> resp = GenericResponse.fromError(e.getMessage());
		return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
	}
}
