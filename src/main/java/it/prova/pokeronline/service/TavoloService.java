package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {
	
	//CRUD
	
	List<Tavolo> listAllElements(boolean eager);

	Tavolo caricaSingoloElemento(Long id);

	Tavolo caricaSingoloElementoEager(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance, Tavolo tavoloCaricatoDalDB);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuovi(Long id);

	List<Tavolo> findAllSpecialPlayer(String name);

	Tavolo caricaSingoloElementoSpecialPlayer(Long idTavolo, Long idUtente);
	

}
