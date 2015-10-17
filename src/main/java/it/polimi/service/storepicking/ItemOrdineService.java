
package it.polimi.service.storepicking;

import java.util.List;
import it.polimi.model.storepicking.ItemOrdine;

public interface ItemOrdineService {


    public List<ItemOrdine> findById(Integer itemOrdineId);

    public List<ItemOrdine> find(ItemOrdine itemOrdine);

    public void deleteById(Integer itemOrdineId);

    public ItemOrdine insert(ItemOrdine itemOrdine);

    public ItemOrdine update(ItemOrdine itemOrdine);

}
