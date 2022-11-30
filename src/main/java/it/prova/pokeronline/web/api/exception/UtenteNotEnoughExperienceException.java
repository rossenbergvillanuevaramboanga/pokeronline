package it.prova.pokeronline.web.api.exception;

public class UtenteNotEnoughExperienceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UtenteNotEnoughExperienceException(String message) {
		super(message);
	}

}
