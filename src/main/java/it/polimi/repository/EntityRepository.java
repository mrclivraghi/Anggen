
package it.polimi.repository;

import java.util.List;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.EntityGroup;
import it.polimi.model.entity.Tab;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.model.security.RestrictionEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository
    extends CrudRepository<Entity, Long>
{


    public List<Entity> findByEntityId(Long entityId);

    public List<Entity> findByName(String name);

    public List<Entity> findByEntityGroup(EntityGroup entityGroup);

    @Query("select e from Entity e where  (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:field in elements(e.fieldList)  or :field is null) and (:relationship in elements(e.relationshipList)  or :relationship is null) and (:enumField in elements(e.enumFieldList)  or :enumField is null) and (:tab in elements(e.tabList)  or :tab is null) and (:restrictionEntity in elements(e.restrictionEntityList)  or :restrictionEntity is null) and (:entityGroup=e.entityGroup or :entityGroup is null) ")
    public List<Entity> findByEntityIdAndNameAndFieldAndRelationshipAndEnumFieldAndTabAndRestrictionEntityAndEntityGroup(
        @Param("entityId")
        Long entityId,
        @Param("name")
        String name,
        @Param("field")
        Field field,
        @Param("relationship")
        Relationship relationship,
        @Param("enumField")
        EnumField enumField,
        @Param("tab")
        Tab tab,
        @Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @Param("entityGroup")
        EntityGroup entityGroup);

}
