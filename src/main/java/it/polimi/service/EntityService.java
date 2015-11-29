
package it.polimi.service;

import java.util.List;

import it.polimi.model.entity.Entity;
import it.polimi.searchbean.EntitySearchBean;

public interface EntityService {


    public List<Entity> findById(Long entityId);

    public List<Entity> find(EntitySearchBean entity);

    public void deleteById(Long entityId);

    public Entity insert(Entity entity);

    public Entity update(Entity entity);

}
