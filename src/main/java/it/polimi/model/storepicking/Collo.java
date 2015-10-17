package it.polimi.model.storepicking;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * The persistent class for the COLLO database table.
 * 
 */
@Entity
@Table(name="COLLO")
public class Collo implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_COLLO", unique=true, nullable=false)
	private Integer colloId;

	@Column(length=100)
	private String codice;

	@Column(name="CUTOFF_ROADNET")
	private Integer cutoffRoadnet;

	@Column(name="ORDINAMENTO_REPORT", length=20)
	private String ordinamentoReport;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_GENERAZIONE")
	private Date tsGenerazione;

	//bi-directional many-to-one association to Ordine
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade({CascadeType.PERSIST})
	@JoinColumn(name="ordine_id_ordine")
	private Ordine ordine;

	public Collo() {
	}

	public Integer getColloId() {
		return colloId;
	}

	public void setColloId(Integer colloId) {
		this.colloId = colloId;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Integer getCutoffRoadnet() {
		return this.cutoffRoadnet;
	}

	public void setCutoffRoadnet(Integer cutoffRoadnet) {
		this.cutoffRoadnet = cutoffRoadnet;
	}

	public String getOrdinamentoReport() {
		return this.ordinamentoReport;
	}

	public void setOrdinamentoReport(String ordinamentoReport) {
		this.ordinamentoReport = ordinamentoReport;
	}

	public Date getTsGenerazione() {
		return this.tsGenerazione;
	}

	public void setTsGenerazione(Date tsGenerazione) {
		this.tsGenerazione = tsGenerazione;
	}

	public Ordine getOrdine() {
		return this.ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}


}