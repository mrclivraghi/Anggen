
package it.polimi.repository;

import java.util.List;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.Tab;
import it.polimi.model.field.Annotation;
import it.polimi.model.field.Field;
import it.polimi.model.security.RestrictionField;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository
    extends CrudRepository<Field, Long>
{


    public List<Field> findByFieldId(Long fieldId);

    public List<Field> findByName(String name);

    public List<Field> findByEntity(Entity entity);

    public List<Field> findByFieldType(Integer fieldType);

    public List<Field> findByTab(Tab tab);

    @Query("select f from Field f where  (:fieldId is null or cast(:fieldId as string)=cast(f.fieldId as string)) and (:name is null or :name='' or cast(:name as string)=f.name) and (:entity=f.entity or :entity is null) and (:fieldType is null or cast(:fieldType as string)=cast(f.fieldType as string)) and (:annotation in elements(f.annotationList)  or :annotation is null) and (:restrictionField in elements(f.restrictionFieldList)  or :restrictionField is null) and (:tab=f.tab or :tab is null) ")
    public List<Field> findByFieldIdAndNameAndEntityAndFieldTypeAndAnnotationAndRestrictionFieldAndTab(
        @Param("fieldId")
        Long fieldId,
        @Param("name")
        String name,
        @Param("entity")
        Entity entity,
        @Param("fieldType")
        Integer fieldType,
        @Param("annotation")
        Annotation annotation,
        @Param("restrictionField")
        RestrictionField restrictionField,
        @Param("tab")
        Tab tab);

}
