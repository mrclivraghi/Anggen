
package it.polimi.service.entity;

import java.util.List;

import it.polimi.repository.entity.EntityRepository;
import it.polimi.repository.entity.TabRepository;
import it.polimi.repository.field.EnumFieldRepository;
import it.polimi.repository.field.FieldRepository;
import it.polimi.repository.relationship.RelationshipRepository;
import it.polimi.repository.security.RestrictionEntityRepository;
import it.polimi.searchbean.entity.EntitySearchBean;
import it.polimi.service.entity.EntityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityServiceImpl
    implements EntityService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EntityRepository entityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RelationshipRepository relationshipRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public TabRepository tabRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityRepository restrictionEntityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public FieldRepository fieldRepository;

    @Override
    public List<it.polimi.model.entity.Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public List<it.polimi.model.entity.Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndNameAndRelationshipAndEnumFieldAndTabAndRestrictionEntityAndEntityGroupAndFieldAndSecurityType(entity.getEntityId(),entity.getName(),entity.getRelationshipList()==null? null :entity.getRelationshipList().get(0),entity.getEnumFieldList()==null? null :entity.getEnumFieldList().get(0),entity.getTabList()==null? null :entity.getTabList().get(0),entity.getRestrictionEntityList()==null? null :entity.getRestrictionEntityList().get(0),entity.getEntityGroup(),entity.getFieldList()==null? null :entity.getFieldList().get(0), (entity.getSecurityType()==null)? null : entity.getSecurityType().getValue());
    }

    @Override
    public void deleteById(Long entityId) {
        entityRepository.delete(entityId);
        return;
    }

    @Override
    public it.polimi.model.entity.Entity insert(it.polimi.model.entity.Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    @Transactional
    public it.polimi.model.entity.Entity update(it.polimi.model.entity.Entity entity) {
        if (entity.getRelationshipList()!=null)
        for (it.polimi.model.relationship.Relationship relationship: entity.getRelationshipList())
        {
        relationship.setEntity(entity);
        }
        if (entity.getEnumFieldList()!=null)
        for (it.polimi.model.field.EnumField enumField: entity.getEnumFieldList())
        {
        enumField.setEntity(entity);
        }
        if (entity.getTabList()!=null)
        for (it.polimi.model.entity.Tab tab: entity.getTabList())
        {
        tab.setEntity(entity);
        }
        if (entity.getRestrictionEntityList()!=null)
        for (it.polimi.model.security.RestrictionEntity restrictionEntity: entity.getRestrictionEntityList())
        {
        restrictionEntity.setEntity(entity);
        }
        if (entity.getFieldList()!=null)
        for (it.polimi.model.field.Field field: entity.getFieldList())
        {
        field.setEntity(entity);
        }
        it.polimi.model.entity.Entity returnedEntity=entityRepository.save(entity);
        if (entity.getEntityGroup()!=null)
        {
        List<it.polimi.model.entity.Entity> entityList = entityRepository.findByEntityGroup( entity.getEntityGroup());
        if (!entityList.contains(returnedEntity))
        entityList.add(returnedEntity);
        returnedEntity.getEntityGroup().setEntityList(entityList);
        }
         return returnedEntity;
    }

}
