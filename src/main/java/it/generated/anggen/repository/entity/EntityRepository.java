
package it.generated.anggen.repository.entity;

import java.util.List;
import it.generated.anggen.model.entity.Tab;
import it.generated.anggen.model.field.EnumField;
import it.generated.anggen.model.field.Field;
import it.generated.anggen.model.relationship.Relationship;
import it.generated.anggen.model.security.RestrictionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository
    extends CrudRepository<it.generated.anggen.model.entity.Entity, java.lang.Long>
{


    public List<it.generated.anggen.model.entity.Entity> findByName(java.lang.String name);

    public List<it.generated.anggen.model.entity.Entity> findByEntityId(java.lang.Long entityId);

    public List<it.generated.anggen.model.entity.Entity> findByEntityGroup(it.generated.anggen.model.entity.EntityGroup entityGroup);

    @Query("select e from Entity e where  (:name is null or :name='' or cast(:name as string)=e.name) and (:entityId is null or cast(:entityId as string)=cast(e.entityId as string)) and (:entityGroup=e.entityGroup or :entityGroup is null) and (:restrictionEntity in elements(e.restrictionEntityList)  or :restrictionEntity is null) and (:tab in elements(e.tabList)  or :tab is null) and (:enumField in elements(e.enumFieldList)  or :enumField is null) and (:relationship in elements(e.relationshipList)  or :relationship is null) and (:field in elements(e.fieldList)  or :field is null) ")
    public List<it.generated.anggen.model.entity.Entity> findByNameAndEntityIdAndEntityGroupAndRestrictionEntityAndTabAndEnumFieldAndRelationshipAndField(
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("entityId")
        java.lang.Long entityId,
        @org.springframework.data.repository.query.Param("entityGroup")
        it.generated.anggen.model.entity.EntityGroup entityGroup,
        @org.springframework.data.repository.query.Param("restrictionEntity")
        RestrictionEntity restrictionEntity,
        @org.springframework.data.repository.query.Param("tab")
        Tab tab,
        @org.springframework.data.repository.query.Param("enumField")
        EnumField enumField,
        @org.springframework.data.repository.query.Param("relationship")
        Relationship relationship,
        @org.springframework.data.repository.query.Param("field")
        Field field);

}
