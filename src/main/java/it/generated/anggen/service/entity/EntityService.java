
package it.generated.anggen.service.entity;

import java.util.List;
import it.generated.anggen.searchbean.entity.EntitySearchBean;

public interface EntityService {


    public List<it.generated.anggen.model.entity.Entity> findById(Long EntityId);

    public List<it.generated.anggen.model.entity.Entity> find(EntitySearchBean Entity);

    public void deleteById(Long EntityId);

    public it.generated.anggen.model.entity.Entity insert(it.generated.anggen.model.entity.Entity Entity);

    public it.generated.anggen.model.entity.Entity update(it.generated.anggen.model.entity.Entity Entity);

}
