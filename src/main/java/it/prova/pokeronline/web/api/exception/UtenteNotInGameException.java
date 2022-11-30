package it.prova.pokeronline.web.api.exception;

public class UtenteNotInGameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UtenteNotInGameException(String message) {
		super(message);
	}

}
