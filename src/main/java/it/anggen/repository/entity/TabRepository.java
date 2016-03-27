
package it.anggen.repository.entity;

import java.util.List;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TabRepository
    extends JpaRepository<it.anggen.model.entity.Tab, java.lang.Long>
{


    public List<it.anggen.model.entity.Tab> findByTabId(java.lang.Long tabId);

    public List<it.anggen.model.entity.Tab> findByName(java.lang.String name);

    public List<it.anggen.model.entity.Tab> findByEntity(it.anggen.model.entity.Entity entity);

    @Query("select t from Tab t where  (:tabId is null or cast(:tabId as string)=cast(t.tabId as string)) and (:name is null or :name='' or cast(:name as string)=t.name) and (:field in elements(t.fieldList)  or :field is null) and (:enumField in elements(t.enumFieldList)  or :enumField is null) and (:entity=t.entity or :entity is null) and (:relationship in elements(t.relationshipList)  or :relationship is null) ")
    public List<it.anggen.model.entity.Tab> findByTabIdAndNameAndFieldAndEnumFieldAndEntityAndRelationship(
        @org.springframework.data.repository.query.Param("tabId")
        java.lang.Long tabId,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("field")
        Field field,
        @org.springframework.data.repository.query.Param("enumField")
        EnumField enumField,
        @org.springframework.data.repository.query.Param("entity")
        it.anggen.model.entity.Entity entity,
        @org.springframework.data.repository.query.Param("relationship")
        Relationship relationship);

}
