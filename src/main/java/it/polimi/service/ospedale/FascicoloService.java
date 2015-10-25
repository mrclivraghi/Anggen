
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Fascicolo;

public interface FascicoloService {


    public List<Fascicolo> findById(Long fascicoloId);

    public List<Fascicolo> find(Fascicolo fascicolo);

    public void deleteById(Long fascicoloId);

    public Fascicolo insert(Fascicolo fascicolo);

    public Fascicolo update(Fascicolo fascicolo);

}
