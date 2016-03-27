
package it.anggen.service.entity;

import java.util.List;
import it.anggen.repository.entity.EntityGroupRepository;
import it.anggen.repository.entity.EntityRepository;
import it.anggen.repository.security.RestrictionEntityGroupRepository;
import it.anggen.searchbean.entity.EntityGroupSearchBean;
import it.anggen.service.entity.EntityGroupService;
import org.springframework.data.domain.Page;
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
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.entity.EntityGroup> findById(Long entityGroupId) {
        return entityGroupRepository.findByEntityGroupId(entityGroupId);
    }

    @Override
    public Page<it.anggen.model.entity.EntityGroup> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "entityGroupId");
        return entityGroupRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.entity.EntityGroup> find(EntityGroupSearchBean entityGroup) {
        return entityGroupRepository.findByEntityGroupIdAndEntityIdAndNameAndProjectAndEntityAndRestrictionEntityGroup(entityGroup.getEntityGroupId(),entityGroup.getEntityId(),entityGroup.getName(),entityGroup.getProject(),entityGroup.getEntityList()==null? null :entityGroup.getEntityList().get(0),entityGroup.getRestrictionEntityGroupList()==null? null :entityGroup.getRestrictionEntityGroupList().get(0));
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
        if (entityGroup.getEntityList()!=null)
        for (it.anggen.model.entity.Entity entity: entityGroup.getEntityList())
        {
        entity.setEntityGroup(entityGroup);
        }
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup: entityGroup.getRestrictionEntityGroupList())
        {
        restrictionEntityGroup.setEntityGroup(entityGroup);
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
