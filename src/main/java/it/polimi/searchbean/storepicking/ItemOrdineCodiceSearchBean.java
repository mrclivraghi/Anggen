
package it.polimi.searchbean.storepicking;

import java.util.Date;
import it.polimi.model.storepicking.ItemOrdine;

public class ItemOrdineCodiceSearchBean {

    public Integer itemOrdineCodiceId;
    public String barcode;
    public String barcodeRead;
    public Integer daCancellare;
    public Integer idStato;
    public String name;
    public Integer nelCarrello;
    public Integer quantityFinale;
    public Date tsModifica;
    public Double vPrice;
    public String vProductId;
    public Double weightFinale;
    public ItemOrdine itemOrdine;

    public Integer getItemOrdineCodiceId() {
        return this.itemOrdineCodiceId;
    }

    public void setItemOrdineCodiceId(Integer itemOrdineCodiceId) {
        this.itemOrdineCodiceId=itemOrdineCodiceId;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode=barcode;
    }

    public String getBarcodeRead() {
        return this.barcodeRead;
    }

    public void setBarcodeRead(String barcodeRead) {
        this.barcodeRead=barcodeRead;
    }

    public Integer getDaCancellare() {
        return this.daCancellare;
    }

    public void setDaCancellare(Integer daCancellare) {
        this.daCancellare=daCancellare;
    }

    public Integer getIdStato() {
        return this.idStato;
    }

    public void setIdStato(Integer idStato) {
        this.idStato=idStato;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Integer getNelCarrello() {
        return this.nelCarrello;
    }

    public void setNelCarrello(Integer nelCarrello) {
        this.nelCarrello=nelCarrello;
    }

    public Integer getQuantityFinale() {
        return this.quantityFinale;
    }

    public void setQuantityFinale(Integer quantityFinale) {
        this.quantityFinale=quantityFinale;
    }

    public Date getTsModifica() {
        return this.tsModifica;
    }

    public void setTsModifica(Date tsModifica) {
        this.tsModifica=tsModifica;
    }

    public Double getVPrice() {
        return this.vPrice;
    }

    public void setVPrice(Double vPrice) {
        this.vPrice=vPrice;
    }

    public String getVProductId() {
        return this.vProductId;
    }

    public void setVProductId(String vProductId) {
        this.vProductId=vProductId;
    }

    public Double getWeightFinale() {
        return this.weightFinale;
    }

    public void setWeightFinale(Double weightFinale) {
        this.weightFinale=weightFinale;
    }

    public ItemOrdine getItemOrdine() {
        return this.itemOrdine;
    }

    public void setItemOrdine(ItemOrdine itemOrdine) {
        this.itemOrdine=itemOrdine;
    }

}
