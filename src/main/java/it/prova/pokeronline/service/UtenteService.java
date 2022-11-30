package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Utente;

public interface UtenteService {
	
	//CRUD
	
	public List<Utente> listAllUtenti();

	public Utente caricaSingoloUtente(Long id);

	public Utente caricaSingoloUtenteConRuoli(Long id);

	public Utente aggiorna(Utente utenteInstance);

	public void inserisciNuovo(Utente utenteInstance);

	public void rimuovi(Utente utenteInstance);
	
	//FIND

	public List<Utente> findByExample(Utente example);

	public Utente findByUsernameAndPassword(String username, String password);

	public Utente findByUsername(String string);

	public void changeUserAbilitation(Long id);

	public void playGame(Utente utenteLoggato);


}
