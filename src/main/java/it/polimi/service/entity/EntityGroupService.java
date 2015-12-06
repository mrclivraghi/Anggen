
package it.polimi.service.entity;

import java.util.List;

import it.polimi.searchbean.entity.EntityGroupSearchBean;

public interface EntityGroupService {


    public List<it.polimi.model.entity.EntityGroup> findById(Long EntityGroupId);

    public List<it.polimi.model.entity.EntityGroup> find(EntityGroupSearchBean EntityGroup);

    public void deleteById(Long EntityGroupId);

    public it.polimi.model.entity.EntityGroup insert(it.polimi.model.entity.EntityGroup EntityGroup);

    public it.polimi.model.entity.EntityGroup update(it.polimi.model.entity.EntityGroup EntityGroup);

}
