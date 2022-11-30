package it.prova.pokeronline.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class TavoloDTO {
	
	private Long id;
	
	@NotBlank(message = "{esperienzaminima.notblank}")
	private Integer esperienzaMinima;
	
	@NotBlank(message = "{ciframinima.notblank}")
	private Integer cifraMinima;
	
	@NotBlank(message = "{denominazione.notblank}")
	private String denominazione;
	
	private LocalDate dateCreated;
	
	// Connection 
	private Set<UtenteDTO> giocatoriDTO = new HashSet<UtenteDTO>(0);
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
			LocalDate dateCreated, Set<UtenteDTO> giocatori, UtenteDTO utenteDTOCreazione) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.giocatoriDTO = giocatori;
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

	

	public Set<UtenteDTO> getGiocatoriDTO() {
		return giocatoriDTO;
	}

	public void setGiocatoriDTO(Set<UtenteDTO> giocatoriDTO) {
		this.giocatoriDTO = giocatoriDTO;
	}

	public UtenteDTO getUtenteDTOCreazione() {
		return utenteDTOCreazione;
	}

	public void setUtenteDTOCreazione(UtenteDTO utenteDTOCreazione) {
		this.utenteDTOCreazione = utenteDTOCreazione;
	}
	
	public Tavolo buildTavoloModel() {
		Tavolo result = new Tavolo(this.id, this.esperienzaMinima, this.cifraMinima, this.denominazione, this.dateCreated);
		
		if(this.giocatoriDTO.size() > 1) {
			Set<Utente> set = result.getGiocatori();
			this.giocatoriDTO.forEach(utente -> set.add(utente.buildUtenteModel(false)));
		}
		
		if(this.utenteDTOCreazione != null)
			result.setUtenteCreazione(this.utenteDTOCreazione.buildUtenteModel(false));
			
		return result;
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel, boolean includeGiocatori) {

		TavoloDTO result = new TavoloDTO(tavoloModel.getId(), tavoloModel.getEsperienzaMinima(),
				tavoloModel.getCifraMinima(), tavoloModel.getDenominazione(), tavoloModel.getDateCreated());

		if (tavoloModel.getUtenteCreazione() != null && tavoloModel.getUtenteCreazione().getId() != null
				&& tavoloModel.getUtenteCreazione().getId() > 0) {
			result.setUtenteDTOCreazione(UtenteDTO.buildUtenteDTOFromModel(tavoloModel.getUtenteCreazione()));
		}

		if (tavoloModel.getGiocatori() != null && !tavoloModel.getGiocatori().isEmpty()) {
			result.setGiocatoriDTO(UtenteDTO.buildUtenteDTOSetFromModelSet(tavoloModel.getGiocatori()));
		}

		return result;
	}
	
	public static TavoloDTO buildTavoloDTOFromModelNoPassword(Tavolo tavoloModel, boolean includeGiocatori) {

		TavoloDTO result = new TavoloDTO(tavoloModel.getId(), tavoloModel.getEsperienzaMinima(),
				tavoloModel.getCifraMinima(), tavoloModel.getDenominazione(), tavoloModel.getDateCreated());

		if (tavoloModel.getUtenteCreazione() != null && tavoloModel.getUtenteCreazione().getId() != null
				&& tavoloModel.getUtenteCreazione().getId() > 0) {
			result.setUtenteDTOCreazione(UtenteDTO.buildUtenteDTOFromModelNoPassword(tavoloModel.getUtenteCreazione()));
		}

		if (tavoloModel.getGiocatori() != null && !tavoloModel.getGiocatori().isEmpty()) {
			result.setGiocatoriDTO(UtenteDTO.buildUtenteDTOSetFromModelSet(tavoloModel.getGiocatori()));
		}

		return result;
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput,
			boolean includeGiocatori) {

		return modelListInput.stream().map(tavoloEntity -> {
			TavoloDTO result = TavoloDTO.buildTavoloDTOFromModelNoPassword(tavoloEntity, includeGiocatori);

			if (includeGiocatori)
				result.setGiocatoriDTO(UtenteDTO.buildUtenteDTOSetFromModelSet(tavoloEntity.getGiocatori()));
			return result;
		}).collect(Collectors.toList());
	}
	
	
	
	
	
	

}
