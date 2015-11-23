
package it.polimi.service.storepicking;

import java.util.List;
import it.polimi.model.storepicking.Ordine;
import it.polimi.searchbean.storepicking.OrdineSearchBean;

public interface OrdineService {


    public List<Ordine> findById(Integer ordineId);

    public List<Ordine> find(OrdineSearchBean ordine);

    public void deleteById(Integer ordineId);

    public Ordine insert(Ordine ordine);

    public Ordine update(Ordine ordine);

}
