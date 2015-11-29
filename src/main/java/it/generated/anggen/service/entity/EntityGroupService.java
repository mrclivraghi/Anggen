
package it.generated.anggen.service.entity;

import java.util.List;
import it.generated.anggen.searchbean.entity.EntityGroupSearchBean;

public interface EntityGroupService {


    public List<it.generated.anggen.model.entity.EntityGroup> findById(Long EntityGroupId);

    public List<it.generated.anggen.model.entity.EntityGroup> find(EntityGroupSearchBean EntityGroup);

    public void deleteById(Long EntityGroupId);

    public it.generated.anggen.model.entity.EntityGroup insert(it.generated.anggen.model.entity.EntityGroup EntityGroup);

    public it.generated.anggen.model.entity.EntityGroup update(it.generated.anggen.model.entity.EntityGroup EntityGroup);

}
