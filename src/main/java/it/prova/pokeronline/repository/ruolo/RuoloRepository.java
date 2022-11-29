package it.prova.pokeronline.repository.ruolo;

import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Ruolo;

public interface RuoloRepository extends CrudRepository<Ruolo, Long> {

	Ruolo findByDescrizioneAndCodice(String descrizione, String codice);

}
