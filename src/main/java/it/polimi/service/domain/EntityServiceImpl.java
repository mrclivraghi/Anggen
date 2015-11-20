
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Entity;
import it.polimi.repository.domain.EntityRepository;
import it.polimi.repository.domain.FieldRepository;
import it.polimi.repository.domain.RelationshipRepository;
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
    public RelationshipRepository relationshipRepository;

    @Override
    public List<Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public List<Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndNameAndFieldAndRelationship(entity.getEntityId(),entity.getName(),entity.getFieldList()==null? null :entity.getFieldList().get(0),entity.getRelationshipList()==null? null :entity.getRelationshipList().get(0));
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
        Entity returnedEntity=entityRepository.save(entity);
         return returnedEntity;
    }

}
