package it.prova.pokeronline.web.api.exception;

public class UtenteCreazioneNotNullForInsertException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UtenteCreazioneNotNullForInsertException(String message) {
		super(message);
	}

}
