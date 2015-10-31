
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Fascicolo;
import it.polimi.searchbean.ospedale.FascicoloSearchBean;

public interface FascicoloService {


    public List<Fascicolo> findById(Long fascicoloId);

    public List<Fascicolo> find(FascicoloSearchBean fascicolo);

    public void deleteById(Long fascicoloId);

    public Fascicolo insert(Fascicolo fascicolo);

    public Fascicolo update(Fascicolo fascicolo);

}
