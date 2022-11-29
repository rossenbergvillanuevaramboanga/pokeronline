package it.prova.pokeronline.service;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
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
	public Tavolo caricaSingoloElemento(Long id) {
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(roleItem -> roleItem.getAuthority().equals(Ruolo.ROLE_SPECIAL_PLAYER))) {
			return repository.findByIdSpecialPlayer(id, utenteRepository
					.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId())
					.orElse(null);
		}

		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElementoEager(Long id) {
		return repository.findByIdEager(id);
	}

	@Override
	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance, Tavolo tavoloCaricatoDalDB) {
		if (tavoloCaricatoDalDB.getGiocatori().size() > 0)
			throw new GiocatoriPresentiException(
					"Impossibile modificare questo Tavolo, sono ancora presenti dei giocatori al suo interno");

		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		if (tavoloInstance.getDateCreated() == null)
			tavoloInstance.setDateCreated(LocalDate.now());

		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long id) {
		Tavolo tavolo = repository.findById(id)
				.orElseThrow(() -> new TavoloNotFoundException("Tavolo con id " + id + " not found"));

		if (tavolo.getGiocatori().size() > 0)
			throw new GiocatoriPresentiException(
					"Impossibile eliminare questo Tavolo, sono ancora presenti dei giocatori al suo interno");

		repository.deleteById(id);
	}

}