package it.prova.pokeronline.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web.api.exception.UtenteCreazioneNotNullForInsertException;

@Controller
@RequestMapping("/api/tavolo")
public class TavoloController {

/*	Classico CRUD (possono farlo sia ADMIN che SPECIAL_PLAYER ma quest’ultimo ha visibilità solo su quelli dove è lui l’utenteCreazione).
 * Non si può cancellare né modificare finché ci sono giocatori a quel Tavolo.
*/
	@Autowired
	private TavoloService tavoloService;
	
	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<TavoloDTO> getAll() {

		/*Se SPECIAL_PLAYER, posso vedere solo i tavoli che hanno creato*/
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
				roleItem -> 
				roleItem.getAuthority().equals(Ruolo.ROLE_SPECIAL_PLAYER)))
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findAllSpecialPlayer(SecurityContextHolder.getContext().getAuthentication().getName()), true);
		
		/*Se ADMIN, posso vedere tutti i tavoli*/
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllElements(false), true);
		
		/*Gli utenti CLASSIC_PLAYER verranno bloccati a livello del filtro*/
	}
	
	@GetMapping("/{id}")
	public TavoloDTO findById(@PathVariable(value = "id", required = true) long id) {
		
		Tavolo tavolo = new Tavolo();
		
		/*Se UTENTE_SPECIAL, posso vedere solo i tavoli che hanno creato*/
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(roleItem -> roleItem.getAuthority().equals(Ruolo.ROLE_SPECIAL_PLAYER))) {
			String username =SecurityContextHolder.getContext().getAuthentication().getName();
			tavolo = tavoloService.caricaSingoloElementoSpecialPlayer(id, utenteService.findByUsername(username).getId());
		}
		
		/*Se ADMIN, posso vedere tutti i tavoli*/
		else tavolo = tavoloService.caricaSingoloElemento(id);
		
		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		return TavoloDTO.buildTavoloDTOFromModelNoPassword(tavolo, true);
		/*Gli utenti CLASSIC_PLAYER verranno bloccati a livello del filtro*/
	}
	
	//Create, Update, Delete 
	
	/*Create può essere effettuato da ADMIN o SPECIAL_PLAYER*/
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {
		
		//Controlli
		if (tavoloInput.getId() != null) throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		if (tavoloInput.getUtenteDTOCreazione() != null) throw new UtenteCreazioneNotNullForInsertException("Non è ammesso fornire un Utente per la creazione");

		// UtenteCreazione = utente in sessione
		tavoloInput.setUtenteDTOCreazione(UtenteDTO.buildUtenteDTOFromModelNoPassword(utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
		// Inserimento nella base dati
		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildTavoloModel());

		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito, false);
	}
	
	
	
	
}
