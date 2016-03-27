
package it.anggen.service.entity;

import java.util.List;
import it.anggen.searchbean.entity.TabSearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface TabService {


    public List<it.anggen.model.entity.Tab> findById(Long TabId);

    public List<it.anggen.model.entity.Tab> find(TabSearchBean Tab);

    public void deleteById(Long TabId);

    public it.anggen.model.entity.Tab insert(it.anggen.model.entity.Tab Tab);

    public it.anggen.model.entity.Tab update(it.anggen.model.entity.Tab Tab);

    public Page<it.anggen.model.entity.Tab> findByPage(
        @PathVariable
        Integer pageNumber);

}
