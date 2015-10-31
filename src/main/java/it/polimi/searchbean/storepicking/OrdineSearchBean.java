
package it.polimi.searchbean.storepicking;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import it.polimi.model.storepicking.Collo;
import it.polimi.model.storepicking.ItemOrdine;

public class OrdineSearchBean {

    public Integer ordineId;
    public String billFile;
    public String cittaSpedizione;
    public String cityId;
    public Date cutoffTime;
    public String emailSpedizione;
    public Boolean fattura;
    public String infoFeedback;
    public Boolean inviatoCassa;
    public String nomeSpedizione;
    public String note;
    public Date pickupTime;
    public Boolean riaperto;
    public Boolean rimandatoAltraData;
    public Integer sentSucceeded;
    public Integer statusPreBill;
    public String statusPreBillErr;
    public String suborderStatusId;
    public String telSpedizione;
    public String tigrosCard;
    public Date timeslotBegin;
    public Date timeslotDate;
    public Date timeslotEnd;
    public Integer tipoSacchetto;
    public BigDecimal totale;
    public Date tsCaricamentoCamion;
    public Date tsFinePreparazione;
    public Date tsInizioPreparazione;
    public Date tsInvioFeedback;
    public Date tsModifica;
    public Date tsRicevuto;
    public Date tsStartPayment;
    public String vOrderId;
    public String vSuborderId;
    public String viaSpedizione;
    public String zoneGroupId;
    public String zoneId;
    public String addressName;
    public String doorBellName;
    public String deliveryCity;
    public String address1;
    public String postalCode;
    public String province;
    public String doorBellNumber;
    public String address2;
    public String referencePhone;
    public BigDecimal latitude;
    public BigDecimal longitude;
    public List<Collo> collo;
    public List<ItemOrdine> itemOrdine;

    public Integer getOrdineId() {
        return this.ordineId;
    }

    public void setOrdineId(Integer ordineId) {
        this.ordineId=ordineId;
    }

    public String getBillFile() {
        return this.billFile;
    }

    public void setBillFile(String billFile) {
        this.billFile=billFile;
    }

    public String getCittaSpedizione() {
        return this.cittaSpedizione;
    }

    public void setCittaSpedizione(String cittaSpedizione) {
        this.cittaSpedizione=cittaSpedizione;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId=cityId;
    }

    public Date getCutoffTime() {
        return this.cutoffTime;
    }

    public void setCutoffTime(Date cutoffTime) {
        this.cutoffTime=cutoffTime;
    }

    public String getEmailSpedizione() {
        return this.emailSpedizione;
    }

    public void setEmailSpedizione(String emailSpedizione) {
        this.emailSpedizione=emailSpedizione;
    }

    public Boolean getFattura() {
        return this.fattura;
    }

    public void setFattura(Boolean fattura) {
        this.fattura=fattura;
    }

    public String getInfoFeedback() {
        return this.infoFeedback;
    }

    public void setInfoFeedback(String infoFeedback) {
        this.infoFeedback=infoFeedback;
    }

    public Boolean getInviatoCassa() {
        return this.inviatoCassa;
    }

    public void setInviatoCassa(Boolean inviatoCassa) {
        this.inviatoCassa=inviatoCassa;
    }

    public String getNomeSpedizione() {
        return this.nomeSpedizione;
    }

    public void setNomeSpedizione(String nomeSpedizione) {
        this.nomeSpedizione=nomeSpedizione;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note=note;
    }

    public Date getPickupTime() {
        return this.pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime=pickupTime;
    }

    public Boolean getRiaperto() {
        return this.riaperto;
    }

    public void setRiaperto(Boolean riaperto) {
        this.riaperto=riaperto;
    }

    public Boolean getRimandatoAltraData() {
        return this.rimandatoAltraData;
    }

    public void setRimandatoAltraData(Boolean rimandatoAltraData) {
        this.rimandatoAltraData=rimandatoAltraData;
    }

    public Integer getSentSucceeded() {
        return this.sentSucceeded;
    }

    public void setSentSucceeded(Integer sentSucceeded) {
        this.sentSucceeded=sentSucceeded;
    }

    public Integer getStatusPreBill() {
        return this.statusPreBill;
    }

    public void setStatusPreBill(Integer statusPreBill) {
        this.statusPreBill=statusPreBill;
    }

    public String getStatusPreBillErr() {
        return this.statusPreBillErr;
    }

    public void setStatusPreBillErr(String statusPreBillErr) {
        this.statusPreBillErr=statusPreBillErr;
    }

    public String getSuborderStatusId() {
        return this.suborderStatusId;
    }

    public void setSuborderStatusId(String suborderStatusId) {
        this.suborderStatusId=suborderStatusId;
    }

    public String getTelSpedizione() {
        return this.telSpedizione;
    }

    public void setTelSpedizione(String telSpedizione) {
        this.telSpedizione=telSpedizione;
    }

    public String getTigrosCard() {
        return this.tigrosCard;
    }

    public void setTigrosCard(String tigrosCard) {
        this.tigrosCard=tigrosCard;
    }

    public Date getTimeslotBegin() {
        return this.timeslotBegin;
    }

    public void setTimeslotBegin(Date timeslotBegin) {
        this.timeslotBegin=timeslotBegin;
    }

    public Date getTimeslotDate() {
        return this.timeslotDate;
    }

    public void setTimeslotDate(Date timeslotDate) {
        this.timeslotDate=timeslotDate;
    }

    public Date getTimeslotEnd() {
        return this.timeslotEnd;
    }

    public void setTimeslotEnd(Date timeslotEnd) {
        this.timeslotEnd=timeslotEnd;
    }

    public Integer getTipoSacchetto() {
        return this.tipoSacchetto;
    }

    public void setTipoSacchetto(Integer tipoSacchetto) {
        this.tipoSacchetto=tipoSacchetto;
    }

    public BigDecimal getTotale() {
        return this.totale;
    }

    public void setTotale(BigDecimal totale) {
        this.totale=totale;
    }

    public Date getTsCaricamentoCamion() {
        return this.tsCaricamentoCamion;
    }

    public void setTsCaricamentoCamion(Date tsCaricamentoCamion) {
        this.tsCaricamentoCamion=tsCaricamentoCamion;
    }

    public Date getTsFinePreparazione() {
        return this.tsFinePreparazione;
    }

    public void setTsFinePreparazione(Date tsFinePreparazione) {
        this.tsFinePreparazione=tsFinePreparazione;
    }

    public Date getTsInizioPreparazione() {
        return this.tsInizioPreparazione;
    }

    public void setTsInizioPreparazione(Date tsInizioPreparazione) {
        this.tsInizioPreparazione=tsInizioPreparazione;
    }

    public Date getTsInvioFeedback() {
        return this.tsInvioFeedback;
    }

    public void setTsInvioFeedback(Date tsInvioFeedback) {
        this.tsInvioFeedback=tsInvioFeedback;
    }

    public Date getTsModifica() {
        return this.tsModifica;
    }

    public void setTsModifica(Date tsModifica) {
        this.tsModifica=tsModifica;
    }

    public Date getTsRicevuto() {
        return this.tsRicevuto;
    }

    public void setTsRicevuto(Date tsRicevuto) {
        this.tsRicevuto=tsRicevuto;
    }

    public Date getTsStartPayment() {
        return this.tsStartPayment;
    }

    public void setTsStartPayment(Date tsStartPayment) {
        this.tsStartPayment=tsStartPayment;
    }

    public String getVOrderId() {
        return this.vOrderId;
    }

    public void setVOrderId(String vOrderId) {
        this.vOrderId=vOrderId;
    }

    public String getVSuborderId() {
        return this.vSuborderId;
    }

    public void setVSuborderId(String vSuborderId) {
        this.vSuborderId=vSuborderId;
    }

    public String getViaSpedizione() {
        return this.viaSpedizione;
    }

    public void setViaSpedizione(String viaSpedizione) {
        this.viaSpedizione=viaSpedizione;
    }

    public String getZoneGroupId() {
        return this.zoneGroupId;
    }

    public void setZoneGroupId(String zoneGroupId) {
        this.zoneGroupId=zoneGroupId;
    }

    public String getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId=zoneId;
    }

    public String getAddressName() {
        return this.addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName=addressName;
    }

    public String getDoorBellName() {
        return this.doorBellName;
    }

    public void setDoorBellName(String doorBellName) {
        this.doorBellName=doorBellName;
    }

    public String getDeliveryCity() {
        return this.deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity=deliveryCity;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1=address1;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode=postalCode;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province=province;
    }

    public String getDoorBellNumber() {
        return this.doorBellNumber;
    }

    public void setDoorBellNumber(String doorBellNumber) {
        this.doorBellNumber=doorBellNumber;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2=address2;
    }

    public String getReferencePhone() {
        return this.referencePhone;
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone=referencePhone;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude=latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude=longitude;
    }

    public List<Collo> getColloList() {
        return this.collo;
    }

    public void setColloList(List<Collo> collo) {
        this.collo=collo;
    }

    public List<ItemOrdine> getItemOrdineList() {
        return this.itemOrdine;
    }

    public void setItemOrdineList(List<ItemOrdine> itemOrdine) {
        this.itemOrdine=itemOrdine;
    }

}
