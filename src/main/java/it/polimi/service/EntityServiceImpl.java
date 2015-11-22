
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EnumField;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.Relationship;
import it.polimi.repository.EntityRepository;
import it.polimi.repository.EnumFieldRepository;
import it.polimi.repository.RelationshipRepository;
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
    public RelationshipRepository relationshipRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;

    @Override
    public List<Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public List<Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndNameAndFieldAndRelationshipAndEnumField(entity.getEntityId(),entity.getName(),entity.getFieldList()==null? null :entity.getFieldList().get(0),entity.getRelationshipList()==null? null :entity.getRelationshipList().get(0),entity.getEnumFieldList()==null? null :entity.getEnumFieldList().get(0));
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
        for (Field field: entity.getFieldList())
        {
        field.setEntity(entity);
        }
        if (entity.getRelationshipList()!=null)
        for (Relationship relationship: entity.getRelationshipList())
        {
        relationship.setEntity(entity);
        }
        if (entity.getEnumFieldList()!=null)
        for (EnumField enumField: entity.getEnumFieldList())
        {
        enumField.setEntity(entity);
        }
        Entity returnedEntity=entityRepository.save(entity);
         return returnedEntity;
    }

}
