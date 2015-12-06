
package it.polimi.service.entity;

import java.util.List;

import it.polimi.searchbean.entity.TabSearchBean;

public interface TabService {


    public List<it.polimi.model.entity.Tab> findById(Long TabId);

    public List<it.polimi.model.entity.Tab> find(TabSearchBean Tab);

    public void deleteById(Long TabId);

    public it.polimi.model.entity.Tab insert(it.polimi.model.entity.Tab Tab);

    public it.polimi.model.entity.Tab update(it.polimi.model.entity.Tab Tab);

}
