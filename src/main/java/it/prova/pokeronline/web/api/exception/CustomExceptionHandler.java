package it.prova.pokeronline.web.api.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);
	}

	// TavoloNotFoundException
	@ExceptionHandler(TavoloNotFoundException.class)
	public ResponseEntity<Object> handleTavoloNotFoundException(TavoloNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	// GiocatoriPresentiException
	@ExceptionHandler(GiocatoriPresentiException.class)
	public ResponseEntity<Object> handleGiocatoriPresentiException(GiocatoriPresentiException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}

	// TavoloNotCreatedByCurrentUserException
	@ExceptionHandler(TavoloNotCreatedByCurrentUserException.class)
	public ResponseEntity<Object> handleTavoloNotCreatedByCurrentUserException(
			TavoloNotCreatedByCurrentUserException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}

	// IdNotNullForInsertException
	@ExceptionHandler(IdNotNullForInsertException.class)
	public ResponseEntity<Object> handleIdNotNullForInsertException(IdNotNullForInsertException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}

	//UtenteCreazioneNotNullForInsertException
	@ExceptionHandler(UtenteCreazioneNotNullForInsertException.class)
	public ResponseEntity<Object> handleUtenteCreazioneNotNullForInsertionException(
			UtenteCreazioneNotNullForInsertException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.FORBIDDEN);

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}
	
	//UtenteCreazioneNotValidException
	@ExceptionHandler(UtenteCreazioneNotValidException.class)
	public ResponseEntity<Object> handleUtenteCreazioneNotValidException(
			UtenteCreazioneNotValidException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.FORBIDDEN);

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}
	
	//CreditNotValidException
	@ExceptionHandler(CreditNotValidException.class)
	public ResponseEntity<Object> handleCreditNotValidException(
			CreditNotValidException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_ACCEPTABLE);

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}
	
	//UtenteAlreadyInGameException
	@ExceptionHandler(UtenteAlreadyInGameException.class)
	public ResponseEntity<Object> handleUtenteAlreadyInGameException(
			UtenteAlreadyInGameException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.FORBIDDEN);

		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}
	
	//UtenteNotInGameException
	@ExceptionHandler(UtenteNotInGameException.class)
	public ResponseEntity<Object> handleUtenteNotInGameException(
			UtenteNotInGameException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	//UtenteNotEnoughExperienceException
	@ExceptionHandler(UtenteNotEnoughExperienceException.class)
	public ResponseEntity<Object> handleUtenteNotEnoughExperienceException(
			UtenteNotEnoughExperienceException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	//UtenteNotEnoughCreditoException
	@ExceptionHandler(UtenteNotEnoughCreditoException.class)
	public ResponseEntity<Object> handleUtenteNotEnoughCreditoException(
			UtenteNotEnoughCreditoException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("credito", "Credito Esaurito");
		body.put("status", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	
	

}
