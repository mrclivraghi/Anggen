
package it.polimi.service;

import java.util.List;
import it.polimi.model.ItemOrdineCodice;

public interface ItemOrdineCodiceService {


    public List<ItemOrdineCodice> findById(Integer itemOrdineCodiceId);

    public List<ItemOrdineCodice> find(ItemOrdineCodice itemOrdineCodice);

    public void deleteById(Integer itemOrdineCodiceId);

    public ItemOrdineCodice insert(ItemOrdineCodice itemOrdineCodice);

    public ItemOrdineCodice update(ItemOrdineCodice itemOrdineCodice);

}
