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
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web.api.exception.UtenteAlreadyInGameException;
import it.prova.pokeronline.web.api.exception.UtenteNotEnoughCreditoException;
import it.prova.pokeronline.web.api.exception.UtenteNotEnoughExperienceException;
import it.prova.pokeronline.web.api.exception.UtenteNotInGameException;

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
	 * COMPRA CREDITO (ti invio di quanto aumentare il mio credito... lasciamo stare
	 * le considerazioni che emergono sul fatto che ci vorrebbe un payment provider
	 * tipo Paypal).
	 */

	@PostMapping("/buy/{credito}")
	@ResponseStatus(HttpStatus.OK)
	public void compraCredito(@PathVariable(value = "credito", required = true) Integer credito) {
		if (credito == null | credito < 1)
			throw new CreditNotValidException("Credito deve essere maggiore di zero.");
		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		utenteLoggato.setCreditoAccumulato(utenteLoggato.getCreditoAccumulato() + credito);
		utenteLoggato = utenteService.aggiorna(utenteLoggato);
	}

	/*
	 * DAMMI IL LAST GAME (restituisce un valore solo se io sono ancora nel set di
	 * qualche tavolo).
	 */
	@GetMapping("/lastgame")
	@ResponseStatus(HttpStatus.OK)
	public TavoloDTO lastGame() {
		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Tavolo lastGameTavolo = tavoloService.findLastGame(utenteLoggato);
		if (lastGameTavolo == null)
			return null;
		return TavoloDTO.buildTavoloDTOFromModelNoPassword(lastGameTavolo, false);
	}

	/*
	 * ABBANDONA PARTITA (il sistema fa il ++ di esperienza. Qui si individua
	 * immediatamente un bug cioè qualcuno per accumulare esperienza potrebbe
	 * entrare e uscire n volte senza giocare. Ma a noi non importa...).
	 */

	@GetMapping("/leavegame")
	@ResponseStatus(HttpStatus.OK)
	public void leaveGame() {

		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (this.lastGame() == null)
			throw new UtenteNotInGameException("L'utente non è in alcuna partita.");

		Tavolo gameTavolo = this.lastGame().buildTavoloModel();

		utenteLoggato.setEsperienzaAccumulata(utenteLoggato.getEsperienzaAccumulata() + 1);
		gameTavolo.getGiocatori().remove(utenteLoggato);

		tavoloService.aggiorna(gameTavolo);
		utenteService.aggiorna(utenteLoggato);
	}

	/*
	 * RICERCA (ricerca solo i tavoli in cui esperienza minima <= esperienza
	 * accumulata).
	 */
	@GetMapping("/findtavoli")
	public List<TavoloDTO> findTavoli() {
		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Tavolo> listaTavoli = tavoloService.findEsperienzaMinima(utenteLoggato.getEsperienzaAccumulata());
		return TavoloDTO.createTavoloDTOListFromModelList(listaTavoli, false);
	}

	/*
	 * GIOCA PARTITA A DETERMINATO TAVOLO inviato come input ovviamente (Gestire a
	 * piacere il caso credito < cifra minima)
	 */
	@GetMapping("/startgame/{idtavolo}")
	@ResponseStatus(HttpStatus.OK)
	public void startGame(@PathVariable(value = "idtavolo", required = true) long idtavolo) {

		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Tavolo tavoloUtente = tavoloService.findLastGame(utenteLoggato);
		Tavolo tavoloGioco = tavoloService.caricaSingoloElementoEager(idtavolo);

		if (tavoloGioco == null)
			throw new TavoloNotFoundException("Tavolo con id " + idtavolo + " non trovato");
		if (tavoloUtente != null && tavoloUtente.getId() != tavoloGioco.getId())
			throw new UtenteAlreadyInGameException("L'utente si trova in un altro tavolo");

		// Controllo se l'utente ha l'esperienza necessaria
		if (utenteLoggato.getEsperienzaAccumulata() < tavoloGioco.getEsperienzaMinima())
			throw new UtenteNotEnoughExperienceException("L'utente non ha l'esperienza necessaria");

		// Controllo se l'utente ha sufficiente credito
		if (utenteLoggato.getCreditoAccumulato() < tavoloGioco.getCifraMinima())
			throw new UtenteNotEnoughCreditoException("L'utente non ha sufficiente credito");

		// Aggiungo l'utente al tavolo se non è già presente
		tavoloGioco.getGiocatori().add(utenteLoggato);
		tavoloService.aggiorna(tavoloGioco);

		// Avviamo il gioco
		utenteService.playGame(utenteLoggato);
	}

}
