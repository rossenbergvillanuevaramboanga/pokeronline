package it.prova.pokeronline.service;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;
import it.prova.pokeronline.web.api.exception.GiocatoriPresentiException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;

@Service
public class TavoloServiceImpl implements TavoloService {
	
	@Autowired
	private TavoloRepository repository;

	@Autowired
	private UtenteRepository utenteRepository;

	
	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> listAllElements(boolean eager) {
		if (eager)
			return repository.findAllEager();
		return (List<Tavolo>) repository.findAll();

	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> findAllSpecialPlayer(String name) {
		// TODO Auto-generated method stub
		return repository.findAllSpecialPlayer(utenteRepository.findByUsername(name).get().getId());
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElementoSpecialPlayer(Long idTavolo,Long idUtente) {
		return repository.findByIdSpecialPlayer(idTavolo, idUtente).orElse(null);
	}
	
	

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElementoEager(Long id) {
		return repository.findByIdEager(id);
	}
	
	//Aggiornamento possibile solo se non ci sono giocatori
	@Override
	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}
	
	
	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		if (tavoloInstance.getDateCreated() == null)
			tavoloInstance.setDateCreated(LocalDate.now());

		return repository.save(tavoloInstance);
	}

	//Eliminazione possibile solo se non ci sono giocatori
	@Override
	@Transactional
	public void rimuovi(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo findByDenominazione(String string) {
		// TODO Auto-generated method stub
		return repository.findByDenominazione(string).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> findEsperienzaMinima(Integer esperienzaAccumulata) {
		// TODO Auto-generated method stub
		return repository.findByEsperienzaMinimaLessThan(esperienzaAccumulata);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo findLastGame(Utente utenteLoggato) {
		// TODO Auto-generated method stub
		return repository.findByGiocatori_id(utenteLoggato.getId()).orElse(null);
	}

	

}
