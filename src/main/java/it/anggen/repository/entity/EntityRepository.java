
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.entity.Tab;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.model.security.RestrictionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository
    extends CrudRepository<it.anggen.model.entity.Entity, java.lang.Long>
{


    public List<it.anggen.model.entity.Entity> findByEntityId(java.lang.Long entityId);

    public List<it.anggen.model.entity.Entity> findByName(java.lang.String name);

    public List<it.anggen.model.entity.Entity> findByDescendantMaxLevel(java.lang.Integer descendantMaxLevel);

    public List<it.anggen.model.entity.Entity> findBySecurityType(java.lang.Integer securityType);

    public List<it.anggen.model.entity.Entity> findByEntityGroup(it.anggen.model.entity.EntityGroup entityGroup);

    @Query("select e from Entity e where  (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:descendantMaxLevel is null or cast(:descendantMaxLevel as string)=cast(e.descendantMaxLevel as string)) and (:securityType is null or cast(:securityType as string)=cast(e.securityType as string)) and (:field in elements(e.fieldList)  or :field is null) and (:entityGroup=e.entityGroup or :entityGroup is null) and (:restrictionEntity in elements(e.restrictionEntityList)  or :restrictionEntity is null) and (:tab in elements(e.tabList)  or :tab is null) and (:enumField in elements(e.enumFieldList)  or :enumField is null) and (:relationship in elements(e.relationshipList)  or :relationship is null) ")
    public List<it.anggen.model.entity.Entity> findByEntityIdAndNameAndDescendantMaxLevelAndSecurityTypeAndFieldAndEntityGroupAndRestrictionEntityAndTabAndEnumFieldAndRelationship(
        @org.springframework.data.repository.query.Param("entityId")
        java.lang.Long entityId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("descendantMaxLevel")
        java.lang.Integer descendantMaxLevel,
        @org.springframework.data.repository.query.Param("securityType")
        java.lang.Integer securityType,
        @org.springframework.data.repository.query.Param("field")
        Field field,
        @org.springframework.data.repository.query.Param("entityGroup")
        it.anggen.model.entity.EntityGroup entityGroup,
        @org.springframework.data.repository.query.Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @org.springframework.data.repository.query.Param("tab")
        Tab tab,
        @org.springframework.data.repository.query.Param("enumField")
        EnumField enumField,
        @org.springframework.data.repository.query.Param("relationship")
        Relationship relationship);

}
