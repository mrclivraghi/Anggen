
package it.polimi.searchbean.ospedale;

import java.util.Date;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.model.ospedale.Paziente;

public class FascicoloSearchBean {

    public Long fascicoloId;
    public Date creationDate;
    public Paziente paziente;
    public Ambulatorio ambulatorio;
    public String tipoIntervento;

    public Long getFascicoloId() {
        return this.fascicoloId;
    }

    public void setFascicoloId(Long fascicoloId) {
        this.fascicoloId=fascicoloId;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate=creationDate;
    }

    public Paziente getPaziente() {
        return this.paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente=paziente;
    }

    public Ambulatorio getAmbulatorio() {
        return this.ambulatorio;
    }

    public void setAmbulatorio(Ambulatorio ambulatorio) {
        this.ambulatorio=ambulatorio;
    }

    public String getTipoIntervento() {
        return this.tipoIntervento;
    }

    public void setTipoIntervento(String tipoIntervento) {
        this.tipoIntervento=tipoIntervento;
    }

}
