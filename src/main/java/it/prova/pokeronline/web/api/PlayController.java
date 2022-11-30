package it.prova.pokeronline.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.CreditNotValidException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/play")
public class PlayController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private TavoloService tavoloService;

	
	/*
	 * COMPRA CREDITO (ti invio di quanto aumentare il mio credito...
	 * lasciamo stare le considerazioni che emergono sul fatto che ci
	 *  vorrebbe un payment provider tipo Paypal).*/
	
	@PostMapping("/buy/{credito}")
	@ResponseStatus(HttpStatus.CREATED)
	public UtenteDTO compraCredito(@PathVariable(value ="credito", required = true) Integer credito) {
		if(credito == null | credito < 1) throw new CreditNotValidException("Credito deve essere maggiore di zero.");
		Utente utenteLoggato = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		utenteLoggato.setCreditoAccumulato( utenteLoggato.getCreditoAccumulato() + credito);
		utenteLoggato = utenteService.aggiorna(utenteLoggato);
		return UtenteDTO.buildUtenteDTOFromModelNoPassword(utenteLoggato);
	}
	
	/*
	 * DAMMI IL LAST GAME (restituisce un valore solo se io sono ancora 
	 * nel set di qualche tavolo).*/
	@GetMapping("/lastgame")
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO lastGame() {
		Utente utenteLoggato = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Tavolo lastGameTavolo = tavoloService.findLastGame(utenteLoggato);
		if (lastGameTavolo == null) return null;
		return TavoloDTO.buildTavoloDTOFromModelNoPassword(lastGameTavolo, false);
	}
	
	/*
	 * ABBANDONA PARTITA (il sistema fa il ++ di esperienza. Qui si individua 
	 * immediatamente un bug cioè qualcuno per accumulare esperienza potrebbe 
	 * entrare e uscire n volte senza giocare. Ma a noi non importa...).*/
	
	@GetMapping("/leavegame")
	@ResponseStatus(HttpStatus.CREATED)
	public UtenteDTO leaveGame() {
		Utente utenteLoggato = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Tavolo gameTavolo = tavoloService.findLastGame(utenteLoggato);
		if (gameTavolo == null) return null;
		utenteLoggato.setEsperienzaAccumulata(utenteLoggato.getEsperienzaAccumulata() + 1);
		gameTavolo.getGiocatori().remove(utenteLoggato);
		tavoloService.aggiorna(gameTavolo);
		utenteService.aggiorna(utenteLoggato);
		return UtenteDTO.buildUtenteDTOFromModelNoPassword(utenteLoggato);
	}
	
	/*RICERCA (ricerca solo i tavoli in cui esperienza minima <= esperienza 
	 * accumulata).*/
	@GetMapping("/findtavoli")
	@ResponseStatus(HttpStatus.CREATED)
	public List<TavoloDTO> findTavoli() {
		Utente utenteLoggato = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Tavolo> listaTavoli = tavoloService.findEsperienzaMinima(utenteLoggato.getEsperienzaAccumulata()) ;
		return TavoloDTO.createTavoloDTOListFromModelList(listaTavoli, false);
	}
	
	/*
	 * GIOCA PARTITA A DETERMINATO TAVOLO inviato come input ovviamente 
	 * (Gestire a piacere il caso credito < cifra minima)*/
	@PostMapping("/startgame/{idtavolo}")
	@ResponseStatus(HttpStatus.CREATED)
	public UtenteDTO startGame(@PathVariable(value ="idtavolo", required = true) long idtavolo){
		
		Utente utenteLoggato = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
		
		return this.leaveGame();
	}
	

}
