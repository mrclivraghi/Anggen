
package it.polimi.searchbean.ospedale;

import java.util.Date;
import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.model.ospedale.Fascicolo;

public class PazienteSearchBean {

    public Long pazienteId;
    public String nome;
    public String cognome;
    public Date birthDate;
    public List<Fascicolo> fascicolo;
    public List<Ambulatorio> ambulatorio;

    public Long getPazienteId() {
        return this.pazienteId;
    }

    public void setPazienteId(Long pazienteId) {
        this.pazienteId=pazienteId;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome=nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome=cognome;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate=birthDate;
    }

    public List<Fascicolo> getFascicoloList() {
        return this.fascicolo;
    }

    public void setFascicoloList(List<Fascicolo> fascicolo) {
        this.fascicolo=fascicolo;
    }

    public List<Ambulatorio> getAmbulatorioList() {
        return this.ambulatorio;
    }

    public void setAmbulatorioList(List<Ambulatorio> ambulatorio) {
        this.ambulatorio=ambulatorio;
    }

}
