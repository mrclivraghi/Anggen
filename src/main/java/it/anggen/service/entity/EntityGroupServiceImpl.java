
package it.anggen.service.entity;

import java.util.List;
import it.anggen.repository.entity.EntityGroupRepository;
import it.anggen.repository.entity.EntityRepository;
import it.anggen.repository.security.RestrictionEntityGroupRepository;
import it.anggen.searchbean.entity.EntityGroupSearchBean;
import it.anggen.service.entity.EntityGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityGroupServiceImpl
    implements EntityGroupService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EntityGroupRepository entityGroupRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityGroupRepository restrictionEntityGroupRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EntityRepository entityRepository;

    @Override
    public List<it.anggen.model.entity.EntityGroup> findById(Long entityGroupId) {
        return entityGroupRepository.findByEntityGroupId(entityGroupId);
    }

    @Override
    public List<it.anggen.model.entity.EntityGroup> find(EntityGroupSearchBean entityGroup) {
        return entityGroupRepository.findByEntityGroupIdAndNameAndEntityIdAndProjectAndRestrictionEntityGroupAndEntity(entityGroup.getEntityGroupId(),entityGroup.getName(),entityGroup.getEntityId(),entityGroup.getProject(),entityGroup.getRestrictionEntityGroupList()==null? null :entityGroup.getRestrictionEntityGroupList().get(0),entityGroup.getEntityList()==null? null :entityGroup.getEntityList().get(0));
    }

    @Override
    public void deleteById(Long entityGroupId) {
        entityGroupRepository.delete(entityGroupId);
        return;
    }

    @Override
    public it.anggen.model.entity.EntityGroup insert(it.anggen.model.entity.EntityGroup entityGroup) {
        return entityGroupRepository.save(entityGroup);
    }

    @Override
    @Transactional
    public it.anggen.model.entity.EntityGroup update(it.anggen.model.entity.EntityGroup entityGroup) {
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup: entityGroup.getRestrictionEntityGroupList())
        {
        restrictionEntityGroup.setEntityGroup(entityGroup);
        }
        if (entityGroup.getEntityList()!=null)
        for (it.anggen.model.entity.Entity entity: entityGroup.getEntityList())
        {
        entity.setEntityGroup(entityGroup);
        }
        it.anggen.model.entity.EntityGroup returnedEntityGroup=entityGroupRepository.save(entityGroup);
        if (entityGroup.getProject()!=null)
        {
        List<it.anggen.model.entity.EntityGroup> entityGroupList = entityGroupRepository.findByProject( entityGroup.getProject());
        if (!entityGroupList.contains(returnedEntityGroup))
        entityGroupList.add(returnedEntityGroup);
        returnedEntityGroup.getProject().setEntityGroupList(entityGroupList);
        }
         return returnedEntityGroup;
    }

}
