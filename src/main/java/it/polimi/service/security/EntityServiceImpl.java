
package it.polimi.service.security;

import java.util.List;
import it.polimi.model.security.Entity;
import it.polimi.repository.security.EntityRepository;
import it.polimi.repository.security.RoleRepository;
import it.polimi.searchbean.security.EntitySearchBean;
import it.polimi.service.security.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityServiceImpl
    implements EntityService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EntityRepository entityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RoleRepository roleRepository;

    @Override
    public List<Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public List<Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndEntityNameAndRole(entity.getEntityId(),entity.getEntityName(),entity.getRoleList()==null? null :entity.getRoleList().get(0));
    }

    @Override
    public void deleteById(Long entityId) {
        entityRepository.delete(entityId);
        return;
    }

    @Override
    public Entity insert(Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    @Transactional
    public Entity update(Entity entity) {
        if (entity.getRoleList()!=null)
        for (it.polimi.model.security.Role role: entity.getRoleList())
        {
        it.polimi.model.security.Role savedRole = roleRepository.findOne(role.getRoleId());
        Boolean found=false; 
        for (Entity tempEntity : savedRole.getEntityList())
        {
        if (tempEntity.getEntityId().equals(entity.getEntityId()))
        {
        found=true;
        break;
        }
        }
        if (!found)
        savedRole.getEntityList().add(entity);
        }
        Entity returnedEntity=entityRepository.save(entity);
         return returnedEntity;
    }

}
