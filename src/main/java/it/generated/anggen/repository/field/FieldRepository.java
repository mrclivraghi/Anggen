
package it.generated.anggen.repository.field;

import java.util.List;
import it.generated.anggen.model.field.Annotation;
import it.generated.anggen.model.security.RestrictionField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository
    extends CrudRepository<it.generated.anggen.model.field.Field, java.lang.Long>
{


    public List<it.generated.anggen.model.field.Field> findByName(java.lang.String name);

    public List<it.generated.anggen.model.field.Field> findByFieldId(java.lang.Long fieldId);

    public List<it.generated.anggen.model.field.Field> findByTab(it.generated.anggen.model.entity.Tab tab);

    public List<it.generated.anggen.model.field.Field> findByEntity(it.generated.anggen.model.entity.Entity entity);

    public List<it.generated.anggen.model.field.Field> findByFieldType(java.lang.Integer fieldType);

    @Query("select f from Field f where  (:name is null or :name='' or cast(:name as string)=f.name) and (:fieldId is null or cast(:fieldId as string)=cast(f.fieldId as string)) and (:tab=f.tab or :tab is null) and (:restrictionField in elements(f.restrictionFieldList)  or :restrictionField is null) and (:annotation in elements(f.annotationList)  or :annotation is null) and (:entity=f.entity or :entity is null) and (:fieldType is null or cast(:fieldType as string)=cast(f.fieldType as string)) ")
    public List<it.generated.anggen.model.field.Field> findByNameAndFieldIdAndTabAndRestrictionFieldAndAnnotationAndEntityAndFieldType(
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("fieldId")
        java.lang.Long fieldId,
        @org.springframework.data.repository.query.Param("tab")
        it.generated.anggen.model.entity.Tab tab,
        @org.springframework.data.repository.query.Param("restrictionField")
        RestrictionField restrictionField,
        @org.springframework.data.repository.query.Param("annotation")
        Annotation annotation,
        @org.springframework.data.repository.query.Param("entity")
        it.generated.anggen.model.entity.Entity entity,
        @org.springframework.data.repository.query.Param("fieldType")
        java.lang.Integer fieldType);

}
