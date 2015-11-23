
package it.polimi.searchbean.storepicking;

import java.util.Date;
import it.polimi.model.storepicking.Ordine;

public class ColloSearchBean {

    public Integer colloId;
    public String codice;
    public Integer cutoffRoadnet;
    public String ordinamentoReport;
    public Date tsGenerazione;
    public Ordine ordine;

    public Integer getColloId() {
        return this.colloId;
    }

    public void setColloId(Integer colloId) {
        this.colloId=colloId;
    }

    public String getCodice() {
        return this.codice;
    }

    public void setCodice(String codice) {
        this.codice=codice;
    }

    public Integer getCutoffRoadnet() {
        return this.cutoffRoadnet;
    }

    public void setCutoffRoadnet(Integer cutoffRoadnet) {
        this.cutoffRoadnet=cutoffRoadnet;
    }

    public String getOrdinamentoReport() {
        return this.ordinamentoReport;
    }

    public void setOrdinamentoReport(String ordinamentoReport) {
        this.ordinamentoReport=ordinamentoReport;
    }

    public Date getTsGenerazione() {
        return this.tsGenerazione;
    }

    public void setTsGenerazione(Date tsGenerazione) {
        this.tsGenerazione=tsGenerazione;
    }

    public Ordine getOrdine() {
        return this.ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine=ordine;
    }

}
