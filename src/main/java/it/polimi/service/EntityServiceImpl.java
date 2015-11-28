
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.Entity;
import it.polimi.repository.EntityRepository;
import it.polimi.repository.RestrictionEntityRepository;
import it.polimi.searchbean.EntitySearchBean;
import it.polimi.service.EntityService;

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

    @Override
    public List<Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public List<Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndNameAndFieldAndRelationshipAndEnumFieldAndTabAndRestrictionEntityAndEntityGroup(entity.getEntityId(),entity.getName(),entity.getFieldList()==null? null :entity.getFieldList().get(0),entity.getRelationshipList()==null? null :entity.getRelationshipList().get(0),entity.getEnumFieldList()==null? null :entity.getEnumFieldList().get(0),entity.getTabList()==null? null :entity.getTabList().get(0),entity.getRestrictionEntityList()==null? null :entity.getRestrictionEntityList().get(0),entity.getEntityGroup());
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
        if (entity.getFieldList()!=null)
        for (it.polimi.model.domain.Field field: entity.getFieldList())
        {
        field.setEntity(entity);
        }
        if (entity.getRelationshipList()!=null)
        for (it.polimi.model.domain.Relationship relationship: entity.getRelationshipList())
        {
        relationship.setEntity(entity);
        }
        if (entity.getEnumFieldList()!=null)
        for (it.polimi.model.domain.EnumField enumField: entity.getEnumFieldList())
        {
        enumField.setEntity(entity);
        }
        if (entity.getTabList()!=null)
        for (it.polimi.model.domain.Tab tab: entity.getTabList())
        {
        tab.setEntity(entity);
        }
        if (entity.getRestrictionEntityList()!=null)
        for (it.polimi.model.domain.RestrictionEntity restrictionEntity: entity.getRestrictionEntityList())
        {
        restrictionEntity.setEntity(entity);
        }
        Entity returnedEntity=entityRepository.save(entity);
        if (entity.getEntityGroup()!=null)
        {
        List<Entity> entityList = entityRepository.findByEntityGroup( entity.getEntityGroup());
        if (!entityList.contains(returnedEntity))
        entityList.add(returnedEntity);
        returnedEntity.getEntityGroup().setEntityList(entityList);
        }
         return returnedEntity;
    }

}
