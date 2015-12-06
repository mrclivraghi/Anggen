
package it.polimi.service.entity;

import java.util.List;

import it.polimi.searchbean.entity.EntitySearchBean;

public interface EntityService {


    public List<it.polimi.model.entity.Entity> findById(Long EntityId);

    public List<it.polimi.model.entity.Entity> find(EntitySearchBean Entity);

    public void deleteById(Long EntityId);

    public it.polimi.model.entity.Entity insert(it.polimi.model.entity.Entity Entity);

    public it.polimi.model.entity.Entity update(it.polimi.model.entity.Entity Entity);

}
