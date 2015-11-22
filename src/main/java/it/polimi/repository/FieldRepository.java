
package it.polimi.repository;

import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Field;

import java.util.List;

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

    @Query("select f from Field f where  (:fieldId is null or cast(:fieldId as string)=cast(f.fieldId as string)) and (:name is null or :name='' or cast(:name as string)=f.name) and (:entity=f.entity or :entity is null) and (:fieldType is null or cast(:fieldType as string)=cast(f.fieldType as string)) and (:annotation in elements(f.annotationList)  or :annotation is null) ")
    public List<Field> findByFieldIdAndNameAndEntityAndFieldTypeAndAnnotation(
        @Param("fieldId")
        Long fieldId,
        @Param("name")
        String name,
        @Param("entity")
        Entity entity,
        @Param("fieldType")
        Integer fieldType,
        @Param("annotation")
        Annotation annotation);

}
