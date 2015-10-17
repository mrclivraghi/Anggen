package it.polimi.old;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * The persistent class for the ITEM_ORDINE_CODICE database table.
 * 
 */
@Entity
@Table(name="ITEM_ORDINE_CODICE")
public class ItemOrdineCodice  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ITEM_ORDINE_CODICE", unique=true, nullable=false)
	private Integer itemOrdineCodiceId;

	@Column(length=255)
	private String barcode;

	@Column(name="BARCODE_READ", length=255)
	private String barcodeRead;

	@Column(name="DA_CANCELLARE")
	private Integer daCancellare;

	

	@Column(name="ID_STATO")
	private Integer idStato;

	@Column(length=255)
	private String name;

	@Column(name="NEL_CARRELLO")
	private Integer nelCarrello;

	@Column(name="QUANTITY_FINALE")
	private Integer quantityFinale;

	@Version
	@Column(name="TS_MODIFICA")
	private Date tsModifica;

	@Column(name="V_PRICE")
	private Double vPrice;

	@Column(name="V_PRODUCT_ID", length=255)
	private String vProductId;

	@Column(name="WEIGHT_FINALE")
	private Double weightFinale;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade({CascadeType.PERSIST})
	@JoinColumn(name="itemordine_id_item_ordine")
	private ItemOrdine itemOrdine;

	public ItemOrdineCodice() {
	}

	

	public Integer getItemOrdineCodiceId() {
		return itemOrdineCodiceId;
	}



	public void setItemOrdineCodiceId(Integer itemOrdineCodiceId) {
		this.itemOrdineCodiceId = itemOrdineCodiceId;
	}



	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodeRead() {
		return this.barcodeRead;
	}

	public void setBarcodeRead(String barcodeRead) {
		this.barcodeRead = barcodeRead;
	}

	public Integer getDaCancellare() {
		return this.daCancellare;
	}

	public void setDaCancellare(Integer daCancellare) {
		this.daCancellare = daCancellare;
	}

	public Integer getIdStato() {
		return this.idStato;
	}

	public void setIdStato(Integer idStato) {
		this.idStato = idStato;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNelCarrello() {
		return this.nelCarrello;
	}

	public void setNelCarrello(Integer nelCarrello) {
		this.nelCarrello = nelCarrello;
	}

	public Integer getQuantityFinale() {
		return this.quantityFinale;
	}

	public void setQuantityFinale(Integer quantityFinale) {
		this.quantityFinale = quantityFinale;
	}

	public Date getTsModifica() {
		return this.tsModifica;
	}

	public void setTsModifica(Date tsModifica) {
		this.tsModifica = tsModifica;
	}

	public Double getVPrice() {
		return this.vPrice;
	}

	public void setVPrice(Double vPrice) {
		this.vPrice = vPrice;
	}

	public String getVProductId() {
		return this.vProductId;
	}

	public void setVProductId(String vProductId) {
		this.vProductId = vProductId;
	}

	public Double getWeightFinale() {
		return this.weightFinale;
	}

	public void setWeightFinale(Double weightFinale) {
		this.weightFinale = weightFinale;
	}



	/**
	 * @return the itemOrdine
	 */
	public ItemOrdine getItemOrdine() {
		return itemOrdine;
	}



	/**
	 * @param itemOrdine the itemOrdine to set
	 */
	public void setItemOrdine(ItemOrdine itemOrdine) {
		this.itemOrdine = itemOrdine;
	}

}