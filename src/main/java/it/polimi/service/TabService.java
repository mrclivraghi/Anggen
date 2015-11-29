
package it.polimi.service;

import java.util.List;

import it.polimi.model.entity.Tab;
import it.polimi.searchbean.TabSearchBean;

public interface TabService {


    public List<Tab> findById(Long tabId);

    public List<Tab> find(TabSearchBean tab);

    public void deleteById(Long tabId);

    public Tab insert(Tab tab);

    public Tab update(Tab tab);

}
