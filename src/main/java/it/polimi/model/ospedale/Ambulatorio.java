package it.polimi.model.ospedale;

import it.polimi.utils.annotation.DescriptionField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ambulatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_ambulatorio")
	@DescriptionField
	private Long ambulatorioId;
	
	private String nome;
	
	private String indirizzo;

	
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
	
}
