
package it.polimi.service;

import java.util.List;
import it.polimi.model.Ordine;

public interface OrdineService {


    public List<Ordine> findById(Integer ordineId);

    public List<Ordine> find(Ordine ordine);

    public void deleteById(Integer ordineId);

    public Ordine insert(Ordine ordine);

    public Ordine update(Ordine ordine);

}
