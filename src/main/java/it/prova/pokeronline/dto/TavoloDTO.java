package it.prova.pokeronline.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl;

import it.prova.pokeronline.model.Tavolo;
import net.bytebuddy.asm.Advice.This;

public class TavoloDTO {
	
	private Long id;
	
	private Integer esperienzaMinima;
	
	private Integer cifraMinima;
	
	private String denominazione;
	
	private LocalDate dateCreated;
	
	// Connection 
	
	private Set<UtenteDTO> utentiGiocatori = new HashSet<UtenteDTO>(0);
	
	private UtenteDTO utenteDTOCreazione;
	
	public TavoloDTO() {
		// TODO Auto-generated constructor stub
	}

	public TavoloDTO(Long id, Integer esperienzaMinima, Integer cifraMinima, String denominazione,
			LocalDate dateCreated) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
	}

	public TavoloDTO(Long id, Integer esperienzaMinima, Integer cifraMinima, String denominazione,
			LocalDate dateCreated, Set<UtenteDTO> utentiGiocatori, UtenteDTO utenteDTOCreazione) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.utentiGiocatori = utentiGiocatori;
		this.utenteDTOCreazione = utenteDTOCreazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienzaMinima() {
		return esperienzaMinima;
	}

	public void setEsperienzaMinima(Integer esperienzaMinima) {
		this.esperienzaMinima = esperienzaMinima;
	}

	public Integer getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(Integer cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Set<UtenteDTO> getUtentiGiocatori() {
		return utentiGiocatori;
	}

	public void setUtentiGiocatori(Set<UtenteDTO> utentiGiocatori) {
		this.utentiGiocatori = utentiGiocatori;
	}

	public UtenteDTO getUtenteDTOCreazione() {
		return utenteDTOCreazione;
	}

	public void setUtenteDTOCreazione(UtenteDTO utenteDTOCreazione) {
		this.utenteDTOCreazione = utenteDTOCreazione;
	}
	
	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.esperienzaMinima, this.cifraMinima, this.denominazione, this.dateCreated,  this.utenteDTOCreazione.buildUtenteModel(false));
	}
	
	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel, boolean includeUtenteCreazione) {
		TavoloDTO result = new TavoloDTO(tavoloModel.getId(), tavoloModel.getEsperienzaMinima(), tavoloModel.getCifraMinima(), tavoloModel.getDenominazione(), tavoloModel.getDateCreated());
		
		if(includeUtenteCreazione)
			result.setUtenteDTOCreazione(UtenteDTO.buildUtenteDTOFromModel(tavoloModel.getUtenteCreazione()));
		return result;
	}
	
	
	
	
	
	

}
