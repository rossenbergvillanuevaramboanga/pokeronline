package it.prova.pokeronline.web.api.exception;

public class UtenteNotEnoughCreditoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UtenteNotEnoughCreditoException(String message) {
		super(message);
	}

}
