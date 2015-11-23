
package it.polimi.searchbean.storepicking;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import it.polimi.model.storepicking.ItemOrdineCodice;
import it.polimi.model.storepicking.Ordine;

public class ItemOrdineSearchBean {

    public Integer itemOrdineId;
    public String barcode;
    public String carfCanaleLocale;
    public int daCancellare;
    public String format;
    public int idStatoFeedback;
    public int idStatoItemOrdine;
    public int idStatoPreparazione;
    public String infoFeedback;
    public String name;
    public String note;
    public int priorita;
    public int productStatus;
    public int promoPunti;
    public BigDecimal quantityFinale;
    public BigDecimal quantityIniz;
    public int singleUnitWeight;
    public int sostituito;
    public Date tsModifica;
    public Date tsPreparazione;
    public String vOrderItemId;
    public BigDecimal vPrice;
    public String vProductId;
    public BigDecimal weightFinale;
    public BigDecimal weightIniz;
    public Ordine ordine;
    public List<ItemOrdineCodice> itemOrdineCodiceList;

    public Integer getItemOrdineId() {
        return this.itemOrdineId;
    }

    public void setItemOrdineId(Integer itemOrdineId) {
        this.itemOrdineId=itemOrdineId;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode=barcode;
    }

    public String getCarfCanaleLocale() {
        return this.carfCanaleLocale;
    }

    public void setCarfCanaleLocale(String carfCanaleLocale) {
        this.carfCanaleLocale=carfCanaleLocale;
    }

    public int getDaCancellare() {
        return this.daCancellare;
    }

    public void setDaCancellare(int daCancellare) {
        this.daCancellare=daCancellare;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format=format;
    }

    public int getIdStatoFeedback() {
        return this.idStatoFeedback;
    }

    public void setIdStatoFeedback(int idStatoFeedback) {
        this.idStatoFeedback=idStatoFeedback;
    }

    public int getIdStatoItemOrdine() {
        return this.idStatoItemOrdine;
    }

    public void setIdStatoItemOrdine(int idStatoItemOrdine) {
        this.idStatoItemOrdine=idStatoItemOrdine;
    }

    public int getIdStatoPreparazione() {
        return this.idStatoPreparazione;
    }

    public void setIdStatoPreparazione(int idStatoPreparazione) {
        this.idStatoPreparazione=idStatoPreparazione;
    }

    public String getInfoFeedback() {
        return this.infoFeedback;
    }

    public void setInfoFeedback(String infoFeedback) {
        this.infoFeedback=infoFeedback;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note=note;
    }

    public int getPriorita() {
        return this.priorita;
    }

    public void setPriorita(int priorita) {
        this.priorita=priorita;
    }

    public int getProductStatus() {
        return this.productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus=productStatus;
    }

    public int getPromoPunti() {
        return this.promoPunti;
    }

    public void setPromoPunti(int promoPunti) {
        this.promoPunti=promoPunti;
    }

    public BigDecimal getQuantityFinale() {
        return this.quantityFinale;
    }

    public void setQuantityFinale(BigDecimal quantityFinale) {
        this.quantityFinale=quantityFinale;
    }

    public BigDecimal getQuantityIniz() {
        return this.quantityIniz;
    }

    public void setQuantityIniz(BigDecimal quantityIniz) {
        this.quantityIniz=quantityIniz;
    }

    public int getSingleUnitWeight() {
        return this.singleUnitWeight;
    }

    public void setSingleUnitWeight(int singleUnitWeight) {
        this.singleUnitWeight=singleUnitWeight;
    }

    public int getSostituito() {
        return this.sostituito;
    }

    public void setSostituito(int sostituito) {
        this.sostituito=sostituito;
    }

    public Date getTsModifica() {
        return this.tsModifica;
    }

    public void setTsModifica(Date tsModifica) {
        this.tsModifica=tsModifica;
    }

    public Date getTsPreparazione() {
        return this.tsPreparazione;
    }

    public void setTsPreparazione(Date tsPreparazione) {
        this.tsPreparazione=tsPreparazione;
    }

    public String getVOrderItemId() {
        return this.vOrderItemId;
    }

    public void setVOrderItemId(String vOrderItemId) {
        this.vOrderItemId=vOrderItemId;
    }

    public BigDecimal getVPrice() {
        return this.vPrice;
    }

    public void setVPrice(BigDecimal vPrice) {
        this.vPrice=vPrice;
    }

    public String getVProductId() {
        return this.vProductId;
    }

    public void setVProductId(String vProductId) {
        this.vProductId=vProductId;
    }

    public BigDecimal getWeightFinale() {
        return this.weightFinale;
    }

    public void setWeightFinale(BigDecimal weightFinale) {
        this.weightFinale=weightFinale;
    }

    public BigDecimal getWeightIniz() {
        return this.weightIniz;
    }

    public void setWeightIniz(BigDecimal weightIniz) {
        this.weightIniz=weightIniz;
    }

    public Ordine getOrdine() {
        return this.ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine=ordine;
    }

    public List<ItemOrdineCodice> getItemOrdineCodiceList() {
        return this.itemOrdineCodiceList;
    }

    public void setItemOrdineCodiceList(List<ItemOrdineCodice> itemOrdineCodiceList) {
        this.itemOrdineCodiceList=itemOrdineCodiceList;
    }

}
