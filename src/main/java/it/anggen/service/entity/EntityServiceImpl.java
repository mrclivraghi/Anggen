
package it.anggen.service.entity;

import java.util.List;
import it.anggen.repository.entity.EntityRepository;
import it.anggen.repository.entity.TabRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.anggen.repository.field.FieldRepository;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.repository.security.RestrictionEntityRepository;
import it.anggen.searchbean.entity.EntitySearchBean;
import it.anggen.service.entity.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityServiceImpl
    implements EntityService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EntityRepository entityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityRepository restrictionEntityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public FieldRepository fieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public TabRepository tabRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RelationshipRepository relationshipRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.entity.Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public Page<it.anggen.model.entity.Entity> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "entityId");
        return entityRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.entity.Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndGenerateFrontEndAndNameAndDescendantMaxLevelAndCacheAndDisableViewGenerationAndEnableRestrictionDataAndSecurityTypeAndRestrictionEntityAndFieldAndEnumFieldAndEntityGroupAndTabAndRelationship(entity.getEntityId(),entity.getGenerateFrontEnd(),entity.getName(),entity.getDescendantMaxLevel(),entity.getCache(),entity.getDisableViewGeneration(),entity.getEnableRestrictionData(), (entity.getSecurityType()==null)? null : entity.getSecurityType().getValue(),entity.getRestrictionEntityList()==null? null :entity.getRestrictionEntityList().get(0),entity.getFieldList()==null? null :entity.getFieldList().get(0),entity.getEnumFieldList()==null? null :entity.getEnumFieldList().get(0),entity.getEntityGroup(),entity.getTabList()==null? null :entity.getTabList().get(0),entity.getRelationshipList()==null? null :entity.getRelationshipList().get(0));
    }

    @Override
    public void deleteById(Long entityId) {
        entityRepository.delete(entityId);
        return;
    }

    @Override
    public it.anggen.model.entity.Entity insert(it.anggen.model.entity.Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    @Transactional
    public it.anggen.model.entity.Entity update(it.anggen.model.entity.Entity entity) {
        if (entity.getRestrictionEntityList()!=null)
        for (it.anggen.model.security.RestrictionEntity restrictionEntity: entity.getRestrictionEntityList())
        {
        restrictionEntity.setEntity(entity);
        }
        if (entity.getFieldList()!=null)
        for (it.anggen.model.field.Field field: entity.getFieldList())
        {
        field.setEntity(entity);
        }
        if (entity.getEnumFieldList()!=null)
        for (it.anggen.model.field.EnumField enumField: entity.getEnumFieldList())
        {
        enumField.setEntity(entity);
        }
        if (entity.getTabList()!=null)
        for (it.anggen.model.entity.Tab tab: entity.getTabList())
        {
        tab.setEntity(entity);
        }
        if (entity.getRelationshipList()!=null)
        for (it.anggen.model.relationship.Relationship relationship: entity.getRelationshipList())
        {
        relationship.setEntity(entity);
        }
        it.anggen.model.entity.Entity returnedEntity=entityRepository.save(entity);
        if (entity.getEntityGroup()!=null)
        {
        List<it.anggen.model.entity.Entity> entityList = entityRepository.findByEntityGroup( entity.getEntityGroup());
        if (!entityList.contains(returnedEntity))
        entityList.add(returnedEntity);
        returnedEntity.getEntityGroup().setEntityList(entityList);
        }
         return returnedEntity;
    }

}
