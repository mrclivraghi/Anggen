
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Tab;
import it.polimi.searchbean.domain.TabSearchBean;

public interface TabService {


    public List<Tab> findById(Long tabId);

    public List<Tab> find(TabSearchBean tab);

    public void deleteById(Long tabId);

    public Tab insert(Tab tab);

    public Tab update(Tab tab);

}
