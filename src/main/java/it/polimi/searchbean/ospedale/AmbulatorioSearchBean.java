
package it.polimi.searchbean.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Paziente;

public class AmbulatorioSearchBean {

    public Long ambulatorioId;
    public String nome;
    public String indirizzo;
    public List<Paziente> pazienteList;

    public Long getAmbulatorioId() {
        return this.ambulatorioId;
    }

    public void setAmbulatorioId(Long ambulatorioId) {
        this.ambulatorioId=ambulatorioId;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome=nome;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo=indirizzo;
    }

    public List<Paziente> getPazienteList() {
        return this.pazienteList;
    }

    public void setPazienteList(List<Paziente> pazienteList) {
        this.pazienteList=pazienteList;
    }

}
