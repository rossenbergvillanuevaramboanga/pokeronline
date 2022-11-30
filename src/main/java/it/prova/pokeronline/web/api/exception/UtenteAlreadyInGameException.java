package it.prova.pokeronline.web.api.exception;

public class UtenteAlreadyInGameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UtenteAlreadyInGameException(String message) {
		super(message);
	}

}
