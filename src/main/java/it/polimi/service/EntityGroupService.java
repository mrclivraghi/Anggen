
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.EntityGroup;
import it.polimi.searchbean.EntityGroupSearchBean;

public interface EntityGroupService {


    public List<EntityGroup> findById(Long entityGroupId);

    public List<EntityGroup> find(EntityGroupSearchBean entityGroup);

    public void deleteById(Long entityGroupId);

    public EntityGroup insert(EntityGroup entityGroup);

    public EntityGroup update(EntityGroup entityGroup);

}
