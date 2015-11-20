
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Entity;
import it.polimi.searchbean.domain.EntitySearchBean;

public interface EntityService {


    public List<Entity> findById(Long entityId);

    public List<Entity> find(EntitySearchBean entity);

    public void deleteById(Long entityId);

    public Entity insert(Entity entity);

    public Entity update(Entity entity);

}
