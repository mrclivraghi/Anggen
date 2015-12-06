
package it.polimi.repository.field;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationAttributeRepository
    extends CrudRepository<it.polimi.model.field.AnnotationAttribute, java.lang.Long>
{


    public List<it.polimi.model.field.AnnotationAttribute> findByAnnotationAttributeId(java.lang.Long annotationAttributeId);

    public List<it.polimi.model.field.AnnotationAttribute> findByProperty(java.lang.String property);

    public List<it.polimi.model.field.AnnotationAttribute> findByValue(java.lang.String value);

    public List<it.polimi.model.field.AnnotationAttribute> findByAnnotation(it.polimi.model.field.Annotation annotation);

    @Query("select a from AnnotationAttribute a where  (:annotationAttributeId is null or cast(:annotationAttributeId as string)=cast(a.annotationAttributeId as string)) and (:property is null or :property='' or cast(:property as string)=a.property) and (:value is null or :value='' or cast(:value as string)=a.value) and (:annotation=a.annotation or :annotation is null) ")
    public List<it.polimi.model.field.AnnotationAttribute> findByAnnotationAttributeIdAndPropertyAndValueAndAnnotation(
        @org.springframework.data.repository.query.Param("annotationAttributeId")
        java.lang.Long annotationAttributeId,
        @org.springframework.data.repository.query.Param("property")
        java.lang.String property,
        @org.springframework.data.repository.query.Param("value")
        java.lang.String value,
        @org.springframework.data.repository.query.Param("annotation")
        it.polimi.model.field.Annotation annotation);

}
