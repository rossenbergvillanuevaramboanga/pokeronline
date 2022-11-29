package it.prova.pokeronline.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web.api.exception.UtenteNotFoundException;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {
	
	@Autowired
	private UtenteService utenteService;	
	
	@PostMapping
	public UtenteDTO createUtente(@Valid @RequestBody UtenteDTO utenteInput) {

		if (utenteInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		
		Utente utente = utenteInput.buildUtenteModel(true);
		utenteService.inserisciNuovo(utente);

		return UtenteDTO.buildUtenteDTOFromModel(utente);
	}
	
	@GetMapping("/{id}")
	public UtenteDTO findById(@PathVariable(value = "id", required = true) Long idUtente) {
		
		return UtenteDTO.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(idUtente));
	}
	
	@PostMapping("/search")
	public List<UtenteDTO> search(@RequestBody UtenteDTO example) {
		return UtenteDTO.buildUtenteDTOListFromModelList(utenteService.findByExample(example.buildUtenteModel(true)));
	}
	
	@PutMapping("/{id}")
	public UtenteDTO update(@Valid @RequestBody UtenteDTO utenteInput, @PathVariable(required = true) Long id) {
		Utente utente = utenteService.caricaSingoloUtente(id);

		if (utente == null)
			throw new UtenteNotFoundException("Utente not found con id: " + id);

		utenteInput.setId(id);
		Utente utenteAggiornato = utenteService.aggiorna(utenteInput.buildUtenteModel(true));
		return UtenteDTO.buildUtenteDTOFromModel(utenteAggiornato);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Utente utente = utenteService.caricaSingoloUtente(id);

		if (utente == null)
			throw new UtenteNotFoundException("Utente not found con id: " + id);
		utenteService.rimuovi(utente);
	}

}
