
package it.polimi.service;

import java.util.List;
import it.polimi.model.ItemOrdine;

public interface ItemOrdineService {


    public List<ItemOrdine> findById(Integer itemOrdineId);

    public List<ItemOrdine> find(ItemOrdine itemOrdine);

    public void deleteById(Integer itemOrdineId);

    public ItemOrdine insert(ItemOrdine itemOrdine);

    public ItemOrdine update(ItemOrdine itemOrdine);

}