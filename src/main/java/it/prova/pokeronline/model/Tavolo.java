package it.prova.pokeronline.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "esperienzaminima")
	private Integer esperienzaMinima;
	
	@Column(name = "ciframinima")
	private Integer cifraMinima;
	
	@Column(name = "denominazione")
	private String denominazione;
	
	@Column(name = "datecreated")
	private LocalDate dateCreated;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Utente> giocatori = new HashSet<Utente>(0);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", referencedColumnName = "id", nullable = false)
	private Utente utenteCreazione; 
	
	
	public Tavolo() {
		// TODO Auto-generated constructor stub
	}

	public Tavolo(Long id, Integer esperienzaMinima, Integer cifraMinima, String denominazione,
			Utente utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.utenteCreazione = utenteCreazione;
	}
	
	public Tavolo(Long id, Integer esperienzaMinima, Integer cifraMinima, String denominazione, LocalDate dateCreated) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
	}
	
	public Tavolo(Long id, Integer esperienzaMinima, Integer cifraMinima, String denominazione, LocalDate dateCreated, Utente utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.utenteCreazione = utenteCreazione;
	}
	
	public Tavolo(Integer esperienzaMinima, Integer cifraMinima, String denominazione, LocalDate dateCreated, Utente utenteCreazione) {
		super();
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.utenteCreazione = utenteCreazione;
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

	
	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	public Utente getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(Utente utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
	
	
	
	

}
