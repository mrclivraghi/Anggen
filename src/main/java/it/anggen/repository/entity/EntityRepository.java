
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.entity.Tab;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.model.security.RestrictionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository
    extends JpaRepository<it.anggen.model.entity.Entity, java.lang.Long>
{


    @Query("select e from Entity e")
    public List<it.anggen.model.entity.Entity> findAll();

    public List<it.anggen.model.entity.Entity> findByEntityId(java.lang.Long entityId);

    public List<it.anggen.model.entity.Entity> findByEnableRestrictionData(java.lang.Boolean enableRestrictionData);

    public List<it.anggen.model.entity.Entity> findByDisableViewGeneration(java.lang.Boolean disableViewGeneration);

    public List<it.anggen.model.entity.Entity> findByCache(java.lang.Boolean cache);

    public List<it.anggen.model.entity.Entity> findByDescendantMaxLevel(java.lang.Integer descendantMaxLevel);

    public List<it.anggen.model.entity.Entity> findByName(java.lang.String name);

    public List<it.anggen.model.entity.Entity> findByGenerateFrontEnd(java.lang.Boolean generateFrontEnd);

    public List<it.anggen.model.entity.Entity> findBySecurityType(java.lang.Integer securityType);

    public List<it.anggen.model.entity.Entity> findByEntityGroup(it.anggen.model.entity.EntityGroup entityGroup);

    @Query("select e from Entity e where  (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:enableRestrictionData is null or cast(:enableRestrictionData as string)=cast(e.enableRestrictionData as string)) and (:disableViewGeneration is null or cast(:disableViewGeneration as string)=cast(e.disableViewGeneration as string)) and (:cache is null or cast(:cache as string)=cast(e.cache as string)) and (:descendantMaxLevel is null or cast(:descendantMaxLevel as string)=cast(e.descendantMaxLevel as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:generateFrontEnd is null or cast(:generateFrontEnd as string)=cast(e.generateFrontEnd as string)) and (:securityType is null or cast(:securityType as string)=cast(e.securityType as string)) and (:restrictionEntity in elements(e.restrictionEntityList)  or :restrictionEntity is null) and (:tab in elements(e.tabList)  or :tab is null) and (:entityGroup=e.entityGroup or :entityGroup is null) and (:enumField in elements(e.enumFieldList)  or :enumField is null) and (:field in elements(e.fieldList)  or :field is null) and (:relationship in elements(e.relationshipList)  or :relationship is null) ")
    public List<it.anggen.model.entity.Entity> findByEntityIdAndEnableRestrictionDataAndDisableViewGenerationAndCacheAndDescendantMaxLevelAndNameAndGenerateFrontEndAndSecurityTypeAndRestrictionEntityAndTabAndEntityGroupAndEnumFieldAndFieldAndRelationship(
        @org.springframework.data.repository.query.Param("entityId")
        java.lang.Long entityId,
        @org.springframework.data.repository.query.Param("enableRestrictionData")
        java.lang.Boolean enableRestrictionData,
        @org.springframework.data.repository.query.Param("disableViewGeneration")
        java.lang.Boolean disableViewGeneration,
        @org.springframework.data.repository.query.Param("cache")
        java.lang.Boolean cache,
        @org.springframework.data.repository.query.Param("descendantMaxLevel")
        java.lang.Integer descendantMaxLevel,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("generateFrontEnd")
        java.lang.Boolean generateFrontEnd,
        @org.springframework.data.repository.query.Param("securityType")
        java.lang.Integer securityType,
        @org.springframework.data.repository.query.Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @org.springframework.data.repository.query.Param("tab")
        Tab tab,
        @org.springframework.data.repository.query.Param("entityGroup")
        it.anggen.model.entity.EntityGroup entityGroup,
        @org.springframework.data.repository.query.Param("enumField")
        EnumField enumField,
        @org.springframework.data.repository.query.Param("field")
        Field field,
        @org.springframework.data.repository.query.Param("relationship")
        Relationship relationship);

}
