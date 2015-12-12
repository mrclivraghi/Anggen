
package it.anggen.service.entity;

import java.util.List;
import it.anggen.searchbean.entity.TabSearchBean;

public interface TabService {


    public List<it.anggen.model.entity.Tab> findById(Long TabId);

    public List<it.anggen.model.entity.Tab> find(TabSearchBean Tab);

    public void deleteById(Long TabId);

    public it.anggen.model.entity.Tab insert(it.anggen.model.entity.Tab Tab);

    public it.anggen.model.entity.Tab update(it.anggen.model.entity.Tab Tab);

}
