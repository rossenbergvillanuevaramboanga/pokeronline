package it.prova.pokeronline;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private TavoloService tavoloServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// RUOLO

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic Player", Ruolo.ROLE_PLAYER));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
		}

		// UTENTE

		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", LocalDate.now());
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			admin.setEsperienzaAccumulata(1000);
			admin.setCreditoAccumulato(1000);
			utenteServiceInstance.inserisciNuovo(admin);
			// Attivazione
			admin.setStato(StatoUtente.ATTIVO);
			utenteServiceInstance.aggiorna(admin);
		}

		if (utenteServiceInstance.findByUsername("player") == null) {
			Utente classicPlayer = new Utente("player", "player", "Antonio", "Verdi", LocalDate.now());
			classicPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER));
			classicPlayer.setEsperienzaAccumulata(100);
			classicPlayer.setCreditoAccumulato(1000);
			utenteServiceInstance.inserisciNuovo(classicPlayer);
			// Attivazione
			classicPlayer.setStato(StatoUtente.ATTIVO);
			utenteServiceInstance.aggiorna(classicPlayer);
		}

		if (utenteServiceInstance.findByUsername("specialplayer") == null) {
			Utente specialPlayer = new Utente("specialplayer", "specialplayer", "Massimo", "Giallo", LocalDate.now());
			specialPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
			specialPlayer.setEsperienzaAccumulata(500);
			specialPlayer.setCreditoAccumulato(1000);
			utenteServiceInstance.inserisciNuovo(specialPlayer);
			// Attivazione
			specialPlayer.setStato(StatoUtente.ATTIVO);
			utenteServiceInstance.aggiorna(specialPlayer);
		}

		// TAVOLO

		if (tavoloServiceInstance.findByDenominazione("tavolo1") == null) {
			Tavolo tavolo1 = new Tavolo(0, 100, "tavolo1", LocalDate.now(),
					utenteServiceInstance.findByUsername("admin"));
			tavoloServiceInstance.inserisciNuovo(tavolo1);
		}

		if (tavoloServiceInstance.findByDenominazione("tavolo2") == null) {
			Tavolo tavolo2 = new Tavolo(0, 200, "tavolo2", LocalDate.now(),
					utenteServiceInstance.findByUsername("player"));
			tavoloServiceInstance.inserisciNuovo(tavolo2);
		}

		if (tavoloServiceInstance.findByDenominazione("tavolo3") == null) {
			Tavolo tavolo3 = new Tavolo(0, 300, "tavolo3", LocalDate.now(),
					utenteServiceInstance.findByUsername("specialplayer"));
			tavoloServiceInstance.inserisciNuovo(tavolo3);
		}

	}

}
