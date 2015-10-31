package it.polimi.model.ospedale;

import it.polimi.utils.annotation.DescriptionField;
import it.polimi.utils.annotation.IgnoreSearch;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

@Entity
public class Paziente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_paziente")
	@DescriptionField
	private Long pazienteId;
	
	private String nome;
	
	private String cognome;
	
	@IgnoreSearch
	private Date birthDate;
	
	@OneToMany(fetch=FetchType.EAGER)
	//@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.ospedale.Fascicolo")
	@JoinColumn(name="paziente_id_paziente")
	private List<Fascicolo> fascicoloList;

	@ManyToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.ospedale.Ambulatorio")
	@JoinTable(name="paziente_ambulatorio", joinColumns = {
			@JoinColumn(name="paziente_id") },
			inverseJoinColumns= {
			@JoinColumn(name="ambulatorio_id")
			
	})
	private List<Ambulatorio> ambulatorioList;
	
	/**
	 * @return the pazienteId
	 */
	public Long getPazienteId() {
		return pazienteId;
	}

	/**
	 * @param pazienteId the pazienteId to set
	 */
	public void setPazienteId(Long pazienteId) {
		this.pazienteId = pazienteId;
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
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the fascicoliList
	 */
	public List<Fascicolo> getFascicoloList() {
		return fascicoloList;
	}

	/**
	 * @param fascicoliList the fascicoliList to set
	 */
	public void setFascicoloList(List<Fascicolo> fascicoloList) {
		this.fascicoloList = fascicoloList;
	}

	/**
	 * @return the ambulatorioList
	 */
	public List<Ambulatorio> getAmbulatorioList() {
		return ambulatorioList;
	}

	/**
	 * @param ambulatorioList the ambulatorioList to set
	 */
	public void setAmbulatorioList(List<Ambulatorio> ambulatorioList) {
		this.ambulatorioList = ambulatorioList;
	}
	
}
