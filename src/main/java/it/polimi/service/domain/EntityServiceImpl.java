
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Entity;
import it.polimi.repository.domain.EntityRepository;
import it.polimi.repository.domain.FieldRepository;
import it.polimi.repository.domain.RestrictionRepository;
import it.polimi.searchbean.domain.EntitySearchBean;
import it.polimi.service.domain.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityServiceImpl
    implements EntityService
{

    @org.springframework.beans.factory.annotation.Autowired
    public EntityRepository entityRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public FieldRepository fieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionRepository restrictionRepository;

    @Override
    public List<Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public List<Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndNameAndFieldAndRelationshipAndEnumFieldAndTabAndRestriction(entity.getEntityId(),entity.getName(),entity.getFieldList()==null? null :entity.getFieldList().get(0),entity.getRelationshipList()==null? null :entity.getRelationshipList().get(0),entity.getEnumFieldList()==null? null :entity.getEnumFieldList().get(0),entity.getTabList()==null? null :entity.getTabList().get(0),entity.getRestrictionList()==null? null :entity.getRestrictionList().get(0));
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
        if (entity.getRestrictionList()!=null)
        for (it.polimi.model.domain.Restriction restriction: entity.getRestrictionList())
        {
        restriction.setEntity(entity);
        }
        Entity returnedEntity=entityRepository.save(entity);
         return returnedEntity;
    }

}
