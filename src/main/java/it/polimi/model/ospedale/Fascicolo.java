package it.polimi.model.ospedale;

import it.polimi.utils.annotation.DescriptionField;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

@Entity
public class Fascicolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_fascicolo")
	@DescriptionField
	private Long fascicoloId;
	
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name="paziente_id_paziente")
	private Paziente paziente;
	
	@OneToOne
	@Type(type="it.polimi.model.ospedale.Ambulatorio")
	private Ambulatorio ambulatorio;
	
	private String tipoIntervento;

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the fascicoloId
	 */
	public Long getFascicoloId() {
		return fascicoloId;
	}

	/**
	 * @param fascicoloId the fascicoloId to set
	 */
	public void setFascicoloId(Long fascicoloId) {
		this.fascicoloId = fascicoloId;
	}

	/**
	 * @return the paziente
	 */
	public Paziente getPaziente() {
		return paziente;
	}

	/**
	 * @param paziente the paziente to set
	 */
	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}

	/**
	 * @return the ambulatorio
	 */
	public Ambulatorio getAmbulatorio() {
		return ambulatorio;
	}

	/**
	 * @param ambulatorio the ambulatorio to set
	 */
	public void setAmbulatorio(Ambulatorio ambulatorio) {
		this.ambulatorio = ambulatorio;
	}

	/**
	 * @return the tipoIntervento
	 */
	public String getTipoIntervento() {
		return tipoIntervento;
	}

	/**
	 * @param tipoIntervento the tipoIntervento to set
	 */
	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	
}
