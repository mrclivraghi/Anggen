
package it.generated.anggen.service.entity;

import java.util.List;
import it.generated.anggen.searchbean.entity.TabSearchBean;

public interface TabService {


    public List<it.generated.anggen.model.entity.Tab> findById(Long TabId);

    public List<it.generated.anggen.model.entity.Tab> find(TabSearchBean Tab);

    public void deleteById(Long TabId);

    public it.generated.anggen.model.entity.Tab insert(it.generated.anggen.model.entity.Tab Tab);

    public it.generated.anggen.model.entity.Tab update(it.generated.anggen.model.entity.Tab Tab);

}
