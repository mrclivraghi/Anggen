
package it.anggen.repository.field;

import java.util.List;
import it.anggen.model.field.Annotation;
import it.anggen.model.security.RestrictionField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository
    extends CrudRepository<it.anggen.model.field.Field, java.lang.Long>
{


    public List<it.anggen.model.field.Field> findByFieldId(java.lang.Long fieldId);

    public List<it.anggen.model.field.Field> findByPriority(java.lang.Integer priority);

    public List<it.anggen.model.field.Field> findByName(java.lang.String name);

    public List<it.anggen.model.field.Field> findByFieldType(java.lang.Integer fieldType);

    public List<it.anggen.model.field.Field> findByTab(it.anggen.model.entity.Tab tab);

    public List<it.anggen.model.field.Field> findByEntity(it.anggen.model.entity.Entity entity);

    @Query("select f from Field f where  (:fieldId is null or cast(:fieldId as string)=cast(f.fieldId as string)) and (:priority is null or cast(:priority as string)=cast(f.priority as string)) and (:name is null or :name='' or cast(:name as string)=f.name) and (:fieldType is null or cast(:fieldType as string)=cast(f.fieldType as string)) and (:tab=f.tab or :tab is null) and (:entity=f.entity or :entity is null) and (:annotation in elements(f.annotationList)  or :annotation is null) and (:restrictionField in elements(f.restrictionFieldList)  or :restrictionField is null) ")
    public List<it.anggen.model.field.Field> findByFieldIdAndPriorityAndNameAndFieldTypeAndTabAndEntityAndAnnotationAndRestrictionField(
        @org.springframework.data.repository.query.Param("fieldId")
        java.lang.Long fieldId,
        @org.springframework.data.repository.query.Param("priority")
        java.lang.Integer priority,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("fieldType")
        java.lang.Integer fieldType,
        @org.springframework.data.repository.query.Param("tab")
        it.anggen.model.entity.Tab tab,
        @org.springframework.data.repository.query.Param("entity")
        it.anggen.model.entity.Entity entity,
        @org.springframework.data.repository.query.Param("annotation")
        Annotation annotation,
        @org.springframework.data.repository.query.Param("restrictionField")
        RestrictionField restrictionField);

}
