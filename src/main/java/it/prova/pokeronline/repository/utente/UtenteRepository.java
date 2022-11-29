package it.prova.pokeronline.repository.utente;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
	

	@EntityGraph(attributePaths = { "ruoli" })
	Optional<Utente> findByUsername(String username);

	@Query("from Utente u left join fetch u.ruoli where u.id = ?1")
	Optional<Utente> findByIdConRuoli(Long id);

	Utente findByUsernameAndPassword(String username, String password);

	@EntityGraph(attributePaths = { "ruoli" })
	Utente findByUsernameAndPasswordAndStato(String username, String password, StatoUtente stato);

	@Query(value= "update Utente set creditoaccumulato += ?1 where id = ?2", nativeQuery = true)
	Integer compraCredito(Integer credito, Long id);
}
