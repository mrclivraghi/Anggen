
package it.polimi.service.entity;

import java.util.List;

import it.polimi.repository.entity.EntityGroupRepository;
import it.polimi.repository.entity.EntityRepository;
import it.polimi.repository.security.RestrictionEntityGroupRepository;
import it.polimi.searchbean.entity.EntityGroupSearchBean;
import it.polimi.service.entity.EntityGroupService;

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
    public List<it.polimi.model.entity.EntityGroup> findById(Long entityGroupId) {
        return entityGroupRepository.findByEntityGroupId(entityGroupId);
    }

    @Override
    public List<it.polimi.model.entity.EntityGroup> find(EntityGroupSearchBean entityGroup) {
        return entityGroupRepository.findByEntityIdAndEntityGroupIdAndNameAndEntityAndRestrictionEntityGroupAndProject(entityGroup.getEntityId(),entityGroup.getEntityGroupId(),entityGroup.getName(),entityGroup.getEntityList()==null? null :entityGroup.getEntityList().get(0),entityGroup.getRestrictionEntityGroupList()==null? null :entityGroup.getRestrictionEntityGroupList().get(0),entityGroup.getProject());
    }

    @Override
    public void deleteById(Long entityGroupId) {
        entityGroupRepository.delete(entityGroupId);
        return;
    }

    @Override
    public it.polimi.model.entity.EntityGroup insert(it.polimi.model.entity.EntityGroup entityGroup) {
        return entityGroupRepository.save(entityGroup);
    }

    @Override
    @Transactional
    public it.polimi.model.entity.EntityGroup update(it.polimi.model.entity.EntityGroup entityGroup) {
        if (entityGroup.getEntityList()!=null)
        for (it.polimi.model.entity.Entity entity: entityGroup.getEntityList())
        {
        entity.setEntityGroup(entityGroup);
        }
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.polimi.model.security.RestrictionEntityGroup restrictionEntityGroup: entityGroup.getRestrictionEntityGroupList())
        {
        restrictionEntityGroup.setEntityGroup(entityGroup);
        }
        it.polimi.model.entity.EntityGroup returnedEntityGroup=entityGroupRepository.save(entityGroup);
        if (entityGroup.getProject()!=null)
        {
        List<it.polimi.model.entity.EntityGroup> entityGroupList = entityGroupRepository.findByProject( entityGroup.getProject());
        if (!entityGroupList.contains(returnedEntityGroup))
        entityGroupList.add(returnedEntityGroup);
        returnedEntityGroup.getProject().setEntityGroupList(entityGroupList);
        }
         return returnedEntityGroup;
    }

}
