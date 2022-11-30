package it.prova.pokeronline.web.api.exception;

public class CreditNotValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CreditNotValidException (String message) {
		super(message);
	}

}
