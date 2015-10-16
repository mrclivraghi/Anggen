package it.polimi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;


/**
 * The persistent class for the ITEM_ORDINE database table.
 * 
 */
@Entity
@Table(name="ITEM_ORDINE")
public class ItemOrdine  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ITEM_ORDINE", unique=true, nullable=false)
	private Integer itemOrdineId;

	@Column(length=30)
	private String barcode;

	@NotBlank
	@Column(name="CARF_CANALE_LOCALE",  length=35)
	private String carfCanaleLocale;

	@Column(name="DA_CANCELLARE")
	private int daCancellare;

	@Column(length=200)
	private String format;

	@Column(name="ID_STATO_FEEDBACK")
	private int idStatoFeedback;

	@Column(name="ID_STATO_ITEM_ORDINE")
	private int idStatoItemOrdine;

	@Column(name="ID_STATO_PREPARAZIONE")
	private int idStatoPreparazione;

	@Column(name="INFO_FEEDBACK", length=200)
	private String infoFeedback;

	@Column(length=255)
	private String name;

	@Column(length=200)
	private String note;

	private int priorita;

	@Column(name="PRODUCT_STATUS")
	private int productStatus;

	@Column(name="PROMO_PUNTI")
	private int promoPunti;

	@Column(name="QUANTITY_FINALE", precision=10, scale=3)
	private BigDecimal quantityFinale;

	@Column(name="QUANTITY_INIZ", precision=10, scale=3)
	private BigDecimal quantityIniz;

	@Column(name="SINGLE_UNIT_WEIGHT")
	private int singleUnitWeight;

	private int sostituito;

	@Version
	@Column(name="TS_MODIFICA")
	private Date tsModifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_PREPARAZIONE")
	private Date tsPreparazione;

	@Column(name="V_ORDER_ITEM_ID", length=20)
	private String vOrderItemId;

	@Column(name="V_PRICE", precision=10, scale=3)
	private BigDecimal vPrice;

	@Column(name="V_PRODUCT_ID", length=35)
	private String vProductId;

	@Column(name="WEIGHT_FINALE", precision=10, scale=6)
	private BigDecimal weightFinale;

	@Column(name="WEIGHT_INIZ", precision=10, scale=6)
	private BigDecimal weightIniz;

	//bi-directional many-to-one association to Ordine
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade({CascadeType.PERSIST})
	@JoinColumn(name="ordine_id_ordine")
	private Ordine ordine;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.ItemOrdineCodice")
	@JoinColumn(name="item_ordine_id_item_ordine")
	private List<ItemOrdineCodice> itemOrdineCodiceList;

	public ItemOrdine() {
	}

	public Integer getItemOrdineId() {
		return itemOrdineId;
	}

	public void setItemOrdineId(Integer itemOrdineId) {
		this.itemOrdineId = itemOrdineId;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCarfCanaleLocale() {
		return this.carfCanaleLocale;
	}

	public void setCarfCanaleLocale(String carfCanaleLocale) {
		this.carfCanaleLocale = carfCanaleLocale;
	}

	public int getDaCancellare() {
		return this.daCancellare;
	}

	public void setDaCancellare(int daCancellare) {
		this.daCancellare = daCancellare;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getIdStatoFeedback() {
		return this.idStatoFeedback;
	}

	public void setIdStatoFeedback(int idStatoFeedback) {
		this.idStatoFeedback = idStatoFeedback;
	}

	public int getIdStatoItemOrdine() {
		return this.idStatoItemOrdine;
	}

	public void setIdStatoItemOrdine(int idStatoItemOrdine) {
		this.idStatoItemOrdine = idStatoItemOrdine;
	}

	public int getIdStatoPreparazione() {
		return this.idStatoPreparazione;
	}

	public void setIdStatoPreparazione(int idStatoPreparazione) {
		this.idStatoPreparazione = idStatoPreparazione;
	}

	public String getInfoFeedback() {
		return this.infoFeedback;
	}

	public void setInfoFeedback(String infoFeedback) {
		this.infoFeedback = infoFeedback;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getPriorita() {
		return this.priorita;
	}

	public void setPriorita(int priorita) {
		this.priorita = priorita;
	}

	public int getProductStatus() {
		return this.productStatus;
	}

	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}

	public int getPromoPunti() {
		return this.promoPunti;
	}

	public void setPromoPunti(int promoPunti) {
		this.promoPunti = promoPunti;
	}

	public BigDecimal getQuantityFinale() {
		return this.quantityFinale;
	}

	public void setQuantityFinale(BigDecimal quantityFinale) {
		this.quantityFinale = quantityFinale;
	}

	public BigDecimal getQuantityIniz() {
		return this.quantityIniz;
	}

	public void setQuantityIniz(BigDecimal quantityIniz) {
		this.quantityIniz = quantityIniz;
	}

	public int getSingleUnitWeight() {
		return this.singleUnitWeight;
	}

	public void setSingleUnitWeight(int singleUnitWeight) {
		this.singleUnitWeight = singleUnitWeight;
	}

	public int getSostituito() {
		return this.sostituito;
	}

	public void setSostituito(int sostituito) {
		this.sostituito = sostituito;
	}

	public Date getTsModifica() {
		return this.tsModifica;
	}

	public void setTsModifica(Date tsModifica) {
		this.tsModifica = tsModifica;
	}

	public Date getTsPreparazione() {
		return this.tsPreparazione;
	}

	public void setTsPreparazione(Date tsPreparazione) {
		this.tsPreparazione = tsPreparazione;
	}

	public String getVOrderItemId() {
		return this.vOrderItemId;
	}

	public void setVOrderItemId(String vOrderItemId) {
		this.vOrderItemId = vOrderItemId;
	}

	public BigDecimal getVPrice() {
		return this.vPrice;
	}

	public void setVPrice(BigDecimal vPrice) {
		this.vPrice = vPrice;
	}

	public String getVProductId() {
		return this.vProductId;
	}

	public void setVProductId(String vProductId) {
		this.vProductId = vProductId;
	}

	public BigDecimal getWeightFinale() {
		return this.weightFinale;
	}

	public void setWeightFinale(BigDecimal weightFinale) {
		this.weightFinale = weightFinale;
	}

	public BigDecimal getWeightIniz() {
		return this.weightIniz;
	}

	public void setWeightIniz(BigDecimal weightIniz) {
		this.weightIniz = weightIniz;
	}

	public Ordine getOrdine() {
		return this.ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	/**
	 * @return the itemOrdineCodiceList
	 */
	public List<ItemOrdineCodice> getItemOrdineCodiceList() {
		return itemOrdineCodiceList;
	}

	/**
	 * @param itemOrdineCodiceList the itemOrdineCodiceList to set
	 */
	public void setItemOrdineCodiceList(List<ItemOrdineCodice> itemOrdineCodiceList) {
		this.itemOrdineCodiceList = itemOrdineCodiceList;
	}


}