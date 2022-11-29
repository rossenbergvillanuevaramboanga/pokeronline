package it.prova.pokeronline;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;


@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner{

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic Player", Ruolo.ROLE_PLAYER));
		}
		
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_SPECIAL_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
		}

		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", LocalDate.now());
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			utenteServiceInstance.inserisciNuovo(admin);
			//Attivazione 
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("player") == null) {
			Utente classicPlayer = new Utente("player", "player", "Antonio", "Verdi", LocalDate.now());
			classicPlayer.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(classicPlayer);
			//Attivazione 
			utenteServiceInstance.changeUserAbilitation(classicPlayer.getId());
		}
		
		if (utenteServiceInstance.findByUsername("specialplayer") == null) {
			Utente specialPlayer = new Utente("specialplayer", "specialplayer", "Massimo", "Giallo", LocalDate.now());
			specialPlayer.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(specialPlayer);
			//Attivazione 
			utenteServiceInstance.changeUserAbilitation(specialPlayer.getId());
		}

		
	}

}
