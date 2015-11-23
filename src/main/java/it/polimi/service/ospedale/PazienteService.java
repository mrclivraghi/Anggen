
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Paziente;
import it.polimi.searchbean.ospedale.PazienteSearchBean;

public interface PazienteService {


    public List<Paziente> findById(Long pazienteId);

    public List<Paziente> find(PazienteSearchBean paziente);

    public void deleteById(Long pazienteId);

    public Paziente insert(Paziente paziente);

    public Paziente update(Paziente paziente);

}
