
package it.anggen.service.entity;

import java.util.List;
import it.anggen.searchbean.entity.EntityGroupSearchBean;

public interface EntityGroupService {


    public List<it.anggen.model.entity.EntityGroup> findById(Long EntityGroupId);

    public List<it.anggen.model.entity.EntityGroup> find(EntityGroupSearchBean EntityGroup);

    public void deleteById(Long EntityGroupId);

    public it.anggen.model.entity.EntityGroup insert(it.anggen.model.entity.EntityGroup EntityGroup);

    public it.anggen.model.entity.EntityGroup update(it.anggen.model.entity.EntityGroup EntityGroup);

}
