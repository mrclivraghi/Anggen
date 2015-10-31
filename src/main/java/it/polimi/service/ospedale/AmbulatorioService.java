
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.searchbean.ospedale.AmbulatorioSearchBean;

public interface AmbulatorioService {


    public List<Ambulatorio> findById(Long ambulatorioId);

    public List<Ambulatorio> find(AmbulatorioSearchBean ambulatorio);

    public void deleteById(Long ambulatorioId);

    public Ambulatorio insert(Ambulatorio ambulatorio);

    public Ambulatorio update(Ambulatorio ambulatorio);

}
