
package it.polimi.service.ospedale;

import java.util.List;
import it.polimi.model.ospedale.Ambulatorio;

public interface AmbulatorioService {


    public List<Ambulatorio> findById(Long ambulatorioId);

    public List<Ambulatorio> find(Ambulatorio ambulatorio);

    public void deleteById(Long ambulatorioId);

    public Ambulatorio insert(Ambulatorio ambulatorio);

    public Ambulatorio update(Ambulatorio ambulatorio);

}
