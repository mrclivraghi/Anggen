package it.polimi.model.ospedale;

import java.util.List;

import it.polimi.utils.annotation.DescriptionField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Ambulatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_ambulatorio")
	@DescriptionField
	private Long ambulatorioId;
	
	private String nome;
	
	private String indirizzo;

	
	@ManyToMany(mappedBy = "ambulatorioList",fetch=FetchType.EAGER)
	private List<Paziente> pazienteList;
	
	
	/**
	 * @return the ambulatorioId
	 */
	public Long getAmbulatorioId() {
		return ambulatorioId;
	}

	/**
	 * @param ambulatorioId the ambulatorioId to set
	 */
	public void setAmbulatorioId(Long ambulatorioId) {
		this.ambulatorioId = ambulatorioId;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @param indirizzo the indirizzo to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @return the pazienteList
	 */
	public List<Paziente> getPazienteList() {
		return pazienteList;
	}

	/**
	 * @param pazienteList the pazienteList to set
	 */
	public void setPazienteList(List<Paziente> pazienteList) {
		this.pazienteList = pazienteList;
	}
	
}
