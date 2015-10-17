package it.polimi.model.storepicking;



import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;


/**
 * The persistent class for the ORDINE database table.
 * 
 */
@Entity
@Table(name="ORDINE")
public class Ordine implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ordine", unique=true, nullable=false)
	private Integer ordineId;

	@Size(max=255)
	@Column(name="BILL_FILE", length=255)
	private String billFile;

	@Column(name="CITTA_SPEDIZIONE", length=200)
	private String cittaSpedizione;

	@Column(name="CITY_ID", length=20)
	private String cityId;

	@Column(name="CUTOFF_TIME")
	private Date cutoffTime;

	@Column(name="EMAIL_SPEDIZIONE", length=200)
	private String emailSpedizione;
	
	@Column(name="FATTURA")
	private Boolean fattura;


	@Column(name="INFO_FEEDBACK", length=200)
	private String infoFeedback;

	@Column(name="INVIATO_CASSA")
	private Boolean inviatoCassa;

	@Column(name="NOME_SPEDIZIONE", length=200)
	private String nomeSpedizione;

	@Column(length=200)
	private String note;

	@Column(name="PICKUP_TIME")
	private Date pickupTime;

	private Boolean riaperto;

	@Column(name="RIMANDATO_ALTRA_DATA")
	private Boolean rimandatoAltraData;

	@Column(name="SENT_SUCCEEDED")
	private Integer sentSucceeded;

	@Column(name="STATUS_PRE_BILL")
	private Integer statusPreBill;

	@Column(name="STATUS_PRE_BILL_ERR", length=255)
	private String statusPreBillErr;

	@Column(name="SUBORDER_STATUS_ID", length=20)
	private String suborderStatusId;

	@Column(name="TEL_SPEDIZIONE", length=200)
	private String telSpedizione;

	@Column(name="TIGROS_CARD", length=200)
	private String tigrosCard;

	@Column(name="TIMESLOT_BEGIN")
	private Date timeslotBegin;

	@Temporal(TemporalType.DATE)
	@Column(name="TIMESLOT_DATE")
	private Date timeslotDate;

	@Column(name="TIMESLOT_END")
	private Date timeslotEnd;

	@Column(name="TIPO_SACCHETTO")
	private Integer tipoSacchetto;

	@Column(precision=10, scale=2)
	private BigDecimal totale;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_CARICAMENTO_CAMION")
	private Date tsCaricamentoCamion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_FINE_PREPARAZIONE")
	private Date tsFinePreparazione;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_INIZIO_PREPARAZIONE")
	private Date tsInizioPreparazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_INVIO_FEEDBACK")
	private Date tsInvioFeedback;

	@Version
	@Column(name="TS_MODIFICA")
	private Date tsModifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_RICEVUTO")
	private Date tsRicevuto;

	@Column(name="TS_START_PAYMENT")
	private Date tsStartPayment;

	@Column(name="V_ORDER_ID", length=35)
	private String vOrderId;

	@Column(name="V_SUBORDER_ID", length=35)
	private String vSuborderId;

	@Column(name="VIA_SPEDIZIONE", length=200)
	private String viaSpedizione;

	@Column(name="ZONE_GROUP_ID", length=20)
	private String zoneGroupId;

	@Column(name="ZONE_ID", length=20)
	private String zoneId;
	
	@Column(name="ADDRESS_NAME",length=255)
	private String addressName; // identificativo ufficiom casa etc
	
	@Column(name="DOOR_BELL_NAME",length=255)
    private String doorBellName;
	
	@Column(name="DELIVERY_CITY",length=255)
    private String deliveryCity;
	
	@Column(name="ADDRESS_1",length=255)
    private String address1;
	
	@Column(name="POSTAL_CODE",length=10)
    private String postalCode;
	
	@Column(name="PROVINCE",length=5)
    private String province;
	
	@Column(name="DOOR_BELL_NUMBER",length=255)
    private String doorBellNumber; // scala
	
	@Column(name="ADDRESS_2",length=255)
    private String address2;
	
	@Column(name="REFERENCE_PHONE",length=255)
    private String referencePhone;

	@Column(name="LATITUDE",precision=10, scale=8)
	private BigDecimal latitude;
	
	@Column(name="LONGITUDE",precision=10, scale=8)
	private BigDecimal longitude;
	
	

	//bi-directional many-to-one association to Collo
	@OneToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.Collo")
	@JoinColumn(name="ordine_id_ordine")
	private List<Collo> colloList;

	//bi-directional many-to-one association to ItemOrdine
	@OneToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.ItemOrdine")
	@JoinColumn(name="ordine_id_ordine")
	private List<ItemOrdine> itemOrdineList;

	

	public Ordine() {
	}

	/**
	 * @return the ordineId
	 */
	public Integer getOrdineId() {
		return ordineId;
	}

	/**
	 * @param ordineId the ordineId to set
	 */
	public void setOrdineId(Integer ordineId) {
		this.ordineId = ordineId;
	}

	public String getBillFile() {
		return this.billFile;
	}

	public void setBillFile(String billFile) {
		this.billFile = billFile;
	}

	public String getCittaSpedizione() {
		return this.cittaSpedizione;
	}

	public void setCittaSpedizione(String cittaSpedizione) {
		this.cittaSpedizione = cittaSpedizione;
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Date getCutoffTime() {
		return this.cutoffTime;
	}

	public void setCutoffTime(Date cutoffTime) {
		this.cutoffTime = cutoffTime;
	}

	public String getEmailSpedizione() {
		return this.emailSpedizione;
	}

	public void setEmailSpedizione(String emailSpedizione) {
		this.emailSpedizione = emailSpedizione;
	}

	public Boolean getFattura() {
		return this.fattura;
	}

	public void setFattura(Boolean fattura) {
		this.fattura = fattura;
	}

	public String getInfoFeedback() {
		return this.infoFeedback;
	}

	public void setInfoFeedback(String infoFeedback) {
		this.infoFeedback = infoFeedback;
	}

	public Boolean getInviatoCassa() {
		return this.inviatoCassa;
	}

	public void setInviatoCassa(Boolean inviatoCassa) {
		this.inviatoCassa = inviatoCassa;
	}

	public String getNomeSpedizione() {
		return this.nomeSpedizione;
	}

	public void setNomeSpedizione(String nomeSpedizione) {
		this.nomeSpedizione = nomeSpedizione;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getPickupTime() {
		return this.pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public Boolean getRiaperto() {
		return this.riaperto;
	}

	public void setRiaperto(Boolean riaperto) {
		this.riaperto = riaperto;
	}

	public Boolean getRimandatoAltraData() {
		return this.rimandatoAltraData;
	}

	public void setRimandatoAltraData(Boolean rimandatoAltraData) {
		this.rimandatoAltraData = rimandatoAltraData;
	}

	public Integer getSentSucceeded() {
		return this.sentSucceeded;
	}

	public void setSentSucceeded(Integer sentSucceeded) {
		this.sentSucceeded = sentSucceeded;
	}

	public Integer getStatusPreBill() {
		return this.statusPreBill;
	}

	public void setStatusPreBill(Integer statusPreBill) {
		this.statusPreBill = statusPreBill;
	}

	public String getStatusPreBillErr() {
		return this.statusPreBillErr;
	}

	public void setStatusPreBillErr(String statusPreBillErr) {
		this.statusPreBillErr = statusPreBillErr;
	}

	public String getSuborderStatusId() {
		return this.suborderStatusId;
	}

	public void setSuborderStatusId(String suborderStatusId) {
		this.suborderStatusId = suborderStatusId;
	}

	public String getTelSpedizione() {
		return this.telSpedizione;
	}

	public void setTelSpedizione(String telSpedizione) {
		this.telSpedizione = telSpedizione;
	}

	public String getTigrosCard() {
		return this.tigrosCard;
	}

	public void setTigrosCard(String tigrosCard) {
		this.tigrosCard = tigrosCard;
	}

	public Date getTimeslotBegin() {
		return this.timeslotBegin;
	}

	public void setTimeslotBegin(Date timeslotBegin) {
		this.timeslotBegin = timeslotBegin;
	}

	public Date getTimeslotDate() {
		return this.timeslotDate;
	}

	public void setTimeslotDate(Date timeslotDate) {
		this.timeslotDate = timeslotDate;
	}

	public Date getTimeslotEnd() {
		return this.timeslotEnd;
	}

	public void setTimeslotEnd(Date timeslotEnd) {
		this.timeslotEnd = timeslotEnd;
	}

	public Integer getTipoSacchetto() {
		return this.tipoSacchetto;
	}

	public void setTipoSacchetto(Integer tipoSacchetto) {
		this.tipoSacchetto = tipoSacchetto;
	}

	public BigDecimal getTotale() {
		return this.totale;
	}

	public void setTotale(BigDecimal totale) {
		this.totale = totale;
	}

	public Date getTsCaricamentoCamion() {
		return this.tsCaricamentoCamion;
	}

	public void setTsCaricamentoCamion(Date tsCaricamentoCamion) {
		this.tsCaricamentoCamion = tsCaricamentoCamion;
	}

	public Date getTsFinePreparazione() {
		return this.tsFinePreparazione;
	}

	public void setTsFinePreparazione(Date tsFinePreparazione) {
		this.tsFinePreparazione = tsFinePreparazione;
	}

	public Date getTsInizioPreparazione() {
		return this.tsInizioPreparazione;
	}

	public void setTsInizioPreparazione(Date tsInizioPreparazione) {
		this.tsInizioPreparazione = tsInizioPreparazione;
	}

	public Date getTsInvioFeedback() {
		return this.tsInvioFeedback;
	}

	public void setTsInvioFeedback(Date tsInvioFeedback) {
		this.tsInvioFeedback = tsInvioFeedback;
	}

	public Date getTsModifica() {
		return this.tsModifica;
	}

	public void setTsModifica(Timestamp tsModifica) {
		this.tsModifica = tsModifica;
	}

	public Date getTsRicevuto() {
		return this.tsRicevuto;
	}

	public void setTsRicevuto(Date tsRicevuto) {
		this.tsRicevuto = tsRicevuto;
	}

	public Date getTsStartPayment() {
		return this.tsStartPayment;
	}

	public void setTsStartPayment(Date tsStartPayment) {
		this.tsStartPayment = tsStartPayment;
	}

	public String getVOrderId() {
		return this.vOrderId;
	}

	public void setVOrderId(String vOrderId) {
		this.vOrderId = vOrderId;
	}

	public String getVSuborderId() {
		return this.vSuborderId;
	}

	public void setVSuborderId(String vSuborderId) {
		this.vSuborderId = vSuborderId;
	}

	public String getViaSpedizione() {
		return this.viaSpedizione;
	}

	public void setViaSpedizione(String viaSpedizione) {
		this.viaSpedizione = viaSpedizione;
	}

	public String getZoneGroupId() {
		return this.zoneGroupId;
	}

	public void setZoneGroupId(String zoneGroupId) {
		this.zoneGroupId = zoneGroupId;
	}

	public String getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getDoorBellName() {
		return doorBellName;
	}

	public void setDoorBellName(String doorBellName) {
		this.doorBellName = doorBellName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDoorBellNumber() {
		return doorBellNumber;
	}

	public void setDoorBellNumber(String doorBellNumber) {
		this.doorBellNumber = doorBellNumber;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getReferencePhone() {
		return referencePhone;
	}

	public void setReferencePhone(String referencePhone) {
		this.referencePhone = referencePhone;
	}

	
	public void setLatitude(BigDecimal latitude){
		this.latitude=latitude;
	}
	
	public BigDecimal getLatitude(){
		return this.latitude;
	}
	
	public BigDecimal getLongitude(){
		return this.longitude;
	}
	
	public void setLongitude(BigDecimal longitude){
		this.longitude=longitude;
	}
	
	public void setDeliveryCity(String deliveryCity)
	{
		this.deliveryCity=deliveryCity;
	}
	
	public String getDeliveryCity()
	{
		return this.deliveryCity;
	}

	/**
	 * @return the colloList
	 */
	public List<Collo> getColloList() {
		return colloList;
	}

	/**
	 * @param colloList the colloList to set
	 */
	public void setColloList(List<Collo> colloList) {
		this.colloList = colloList;
	}

	/**
	 * @return the itemOrdineList
	 */
	public List<ItemOrdine> getItemOrdineList() {
		return itemOrdineList;
	}

	/**
	 * @param itemOrdineList the itemOrdineList to set
	 */
	public void setItemOrdineList(List<ItemOrdine> itemOrdineList) {
		this.itemOrdineList = itemOrdineList;
	}
	
	
}