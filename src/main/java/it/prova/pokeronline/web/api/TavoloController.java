package it.prova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.TavoloNotCreatedByCurrentUserException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;

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

		/*Se UTENTE_SPECIAL, posso vedere solo i tavoli che hanno creato*/
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
	}
	
	


}
