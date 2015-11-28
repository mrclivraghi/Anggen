
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.EntityGroup;
import it.polimi.repository.EntityGroupRepository;
import it.polimi.repository.EntityRepository;
import it.polimi.repository.RestrictionEntityGroupRepository;
import it.polimi.searchbean.EntityGroupSearchBean;
import it.polimi.service.EntityGroupService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityGroupServiceImpl
    implements EntityGroupService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EntityGroupRepository entityGroupRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EntityRepository entityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityGroupRepository restrictionEntityGroupRepository;

    @Override
    public List<EntityGroup> findById(Long entityGroupId) {
        return entityGroupRepository.findByEntityGroupId(entityGroupId);
    }

    @Override
    public List<EntityGroup> find(EntityGroupSearchBean entityGroup) {
        return entityGroupRepository.findByEntityGroupIdAndNameAndEntityAndRestrictionEntityGroup(entityGroup.getEntityGroupId(),entityGroup.getName(),entityGroup.getEntityList()==null? null :entityGroup.getEntityList().get(0),entityGroup.getRestrictionEntityGroupList()==null? null :entityGroup.getRestrictionEntityGroupList().get(0));
    }

    @Override
    public void deleteById(Long entityGroupId) {
        entityGroupRepository.delete(entityGroupId);
        return;
    }

    @Override
    public EntityGroup insert(EntityGroup entityGroup) {
        return entityGroupRepository.save(entityGroup);
    }

    @Override
    @Transactional
    public EntityGroup update(EntityGroup entityGroup) {
        if (entityGroup.getEntityList()!=null)
        for (it.polimi.model.domain.Entity entity: entityGroup.getEntityList())
        {
        entity.setEntityGroup(entityGroup);
        }
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.polimi.model.domain.RestrictionEntityGroup restrictionEntityGroup: entityGroup.getRestrictionEntityGroupList())
        {
        restrictionEntityGroup.setEntityGroup(entityGroup);
        }
        EntityGroup returnedEntityGroup=entityGroupRepository.save(entityGroup);
         return returnedEntityGroup;
    }

}
