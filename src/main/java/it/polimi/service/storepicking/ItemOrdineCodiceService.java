
package it.polimi.service.storepicking;

import java.util.List;
import it.polimi.model.storepicking.ItemOrdineCodice;
import it.polimi.searchbean.storepicking.ItemOrdineCodiceSearchBean;

public interface ItemOrdineCodiceService {


    public List<ItemOrdineCodice> findById(Integer itemOrdineCodiceId);

    public List<ItemOrdineCodice> find(ItemOrdineCodiceSearchBean itemOrdineCodice);

    public void deleteById(Integer itemOrdineCodiceId);

    public ItemOrdineCodice insert(ItemOrdineCodice itemOrdineCodice);

    public ItemOrdineCodice update(ItemOrdineCodice itemOrdineCodice);

}
