
package it.generated.anggen.service.entity;

import java.util.List;
import it.generated.anggen.repository.entity.EntityRepository;
import it.generated.anggen.repository.entity.TabRepository;
import it.generated.anggen.repository.field.EnumFieldRepository;
import it.generated.anggen.repository.field.FieldRepository;
import it.generated.anggen.repository.relationship.RelationshipRepository;
import it.generated.anggen.repository.security.RestrictionEntityRepository;
import it.generated.anggen.searchbean.entity.EntitySearchBean;
import it.generated.anggen.service.entity.EntityService;
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
    @org.springframework.beans.factory.annotation.Autowired
    public EnumFieldRepository enumFieldRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public TabRepository tabRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public RestrictionEntityRepository restrictionEntityRepository;

    @Override
    public List<it.generated.anggen.model.entity.Entity> findById(Long entityId) {
        return entityRepository.findByEntityId(entityId);
    }

    @Override
    public List<it.generated.anggen.model.entity.Entity> find(EntitySearchBean entity) {
        return entityRepository.findByEntityIdAndNameAndFieldAndRelationshipAndEnumFieldAndTabAndRestrictionEntityAndEntityGroup(entity.getEntityId(),entity.getName(),entity.getFieldList()==null? null :entity.getFieldList().get(0),entity.getRelationshipList()==null? null :entity.getRelationshipList().get(0),entity.getEnumFieldList()==null? null :entity.getEnumFieldList().get(0),entity.getTabList()==null? null :entity.getTabList().get(0),entity.getRestrictionEntityList()==null? null :entity.getRestrictionEntityList().get(0),entity.getEntityGroup());
    }

    @Override
    public void deleteById(Long entityId) {
        entityRepository.delete(entityId);
        return;
    }

    @Override
    public it.generated.anggen.model.entity.Entity insert(it.generated.anggen.model.entity.Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    @Transactional
    public it.generated.anggen.model.entity.Entity update(it.generated.anggen.model.entity.Entity entity) {
        if (entity.getFieldList()!=null)
        for (it.generated.anggen.model.field.Field field: entity.getFieldList())
        {
        field.setEntity(entity);
        }
        if (entity.getRelationshipList()!=null)
        for (it.generated.anggen.model.relationship.Relationship relationship: entity.getRelationshipList())
        {
        relationship.setEntity(entity);
        }
        if (entity.getEnumFieldList()!=null)
        for (it.generated.anggen.model.field.EnumField enumField: entity.getEnumFieldList())
        {
        enumField.setEntity(entity);
        }
        if (entity.getTabList()!=null)
        for (it.generated.anggen.model.entity.Tab tab: entity.getTabList())
        {
        tab.setEntity(entity);
        }
        if (entity.getRestrictionEntityList()!=null)
        for (it.generated.anggen.model.security.RestrictionEntity restrictionEntity: entity.getRestrictionEntityList())
        {
        restrictionEntity.setEntity(entity);
        }
        it.generated.anggen.model.entity.Entity returnedEntity=entityRepository.save(entity);
        if (entity.getEntityGroup()!=null)
        {
        List<it.generated.anggen.model.entity.Entity> entityList = entityRepository.findByEntityGroup( entity.getEntityGroup());
        if (!entityList.contains(returnedEntity))
        entityList.add(returnedEntity);
        returnedEntity.getEntityGroup().setEntityList(entityList);
        }
         return returnedEntity;
    }

}
