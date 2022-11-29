package it.prova.pokeronline.web.api.exception;

public class TavoloNotCreatedByCurrentUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TavoloNotCreatedByCurrentUserException(String message) {
		super(message);
	}

}
