
package it.anggen.repository.field;

import java.util.List;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.EnumValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumFieldRepository
    extends CrudRepository<it.anggen.model.field.EnumField, java.lang.Long>
{


    public List<it.anggen.model.field.EnumField> findByEnumFieldId(java.lang.Long enumFieldId);

    public List<it.anggen.model.field.EnumField> findByPriority(java.lang.Integer priority);

    public List<it.anggen.model.field.EnumField> findByName(java.lang.String name);

    public List<it.anggen.model.field.EnumField> findByEntity(it.anggen.model.entity.Entity entity);

    public List<it.anggen.model.field.EnumField> findByTab(it.anggen.model.entity.Tab tab);

    @Query("select e from EnumField e where  (:enumFieldId is null or cast(:enumFieldId as string)=cast(e.enumFieldId as string)) and (:priority is null or cast(:priority as string)=cast(e.priority as string)) and (:name is null or :name='' or cast(:name as string)=e.name) and (:enumValue in elements(e.enumValueList)  or :enumValue is null) and (:entity=e.entity or :entity is null) and (:annotation in elements(e.annotationList)  or :annotation is null) and (:tab=e.tab or :tab is null) ")
    public List<it.anggen.model.field.EnumField> findByEnumFieldIdAndPriorityAndNameAndEnumValueAndEntityAndAnnotationAndTab(
        @org.springframework.data.repository.query.Param("enumFieldId")
        java.lang.Long enumFieldId,
        @org.springframework.data.repository.query.Param("priority")
        java.lang.Integer priority,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("enumValue")
        EnumValue enumValue,
        @org.springframework.data.repository.query.Param("entity")
        it.anggen.model.entity.Entity entity,
        @org.springframework.data.repository.query.Param("annotation")
        Annotation annotation,
        @org.springframework.data.repository.query.Param("tab")
        it.anggen.model.entity.Tab tab);

}