
package it.polimi.repository.entity;

import java.util.List;

import it.polimi.model.entity.Tab;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.model.security.RestrictionEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository
    extends CrudRepository<it.polimi.model.entity.Entity, java.lang.Long>
{


    public List<it.polimi.model.entity.Entity> findByEntityId(java.lang.Long entityId);

    public List<it.polimi.model.entity.Entity> findByName(java.lang.String name);

    public List<it.polimi.model.entity.Entity> findByEntityGroup(it.polimi.model.entity.EntityGroup entityGroup);

    public List<it.polimi.model.entity.Entity> findBySecurityType(java.lang.Integer securityType);

    @Query("select e from Entity e where  (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:relationship in elements(e.relationshipList)  or :relationship is null) and (:enumField in elements(e.enumFieldList)  or :enumField is null) and (:tab in elements(e.tabList)  or :tab is null) and (:restrictionEntity in elements(e.restrictionEntityList)  or :restrictionEntity is null) and (:entityGroup=e.entityGroup or :entityGroup is null) and (:field in elements(e.fieldList)  or :field is null) and (:securityType is null or cast(:securityType as string)=cast(e.securityType as string)) ")
    public List<it.polimi.model.entity.Entity> findByEntityIdAndNameAndRelationshipAndEnumFieldAndTabAndRestrictionEntityAndEntityGroupAndFieldAndSecurityType(
        @org.springframework.data.repository.query.Param("entityId")
        java.lang.Long entityId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("relationship")
        Relationship relationship,
        @org.springframework.data.repository.query.Param("enumField")
        EnumField enumField,
        @org.springframework.data.repository.query.Param("tab")
        Tab tab,
        @org.springframework.data.repository.query.Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @org.springframework.data.repository.query.Param("entityGroup")
        it.polimi.model.entity.EntityGroup entityGroup,
        @org.springframework.data.repository.query.Param("field")
        Field field,
        @org.springframework.data.repository.query.Param("securityType")
        java.lang.Integer securityType);

}
