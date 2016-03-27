
package it.anggen.service.entity;

import java.util.List;
import it.anggen.searchbean.entity.EntitySearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface EntityService {


    public List<it.anggen.model.entity.Entity> findById(Long EntityId);

    public List<it.anggen.model.entity.Entity> find(EntitySearchBean Entity);

    public void deleteById(Long EntityId);

    public it.anggen.model.entity.Entity insert(it.anggen.model.entity.Entity Entity);

    public it.anggen.model.entity.Entity update(it.anggen.model.entity.Entity Entity);

    public Page<it.anggen.model.entity.Entity> findByPage(
        @PathVariable
        Integer pageNumber);

}
