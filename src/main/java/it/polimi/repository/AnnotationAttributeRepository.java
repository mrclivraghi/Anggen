
package it.polimi.repository;

import java.util.List;

import it.polimi.model.field.Annotation;
import it.polimi.model.field.AnnotationAttribute;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationAttributeRepository
    extends CrudRepository<AnnotationAttribute, Long>
{


    public List<AnnotationAttribute> findByAnnotationAttributeId(Long annotationAttributeId);

    public List<AnnotationAttribute> findByProperty(String property);

    public List<AnnotationAttribute> findByValue(String value);

    public List<AnnotationAttribute> findByAnnotation(Annotation annotation);

    @Query("select a from AnnotationAttribute a where  (:annotationAttributeId is null or cast(:annotationAttributeId as string)=cast(a.annotationAttributeId as string)) and (:property is null or :property='' or cast(:property as string)=a.property) and (:value is null or :value='' or cast(:value as string)=a.value) and (:annotation=a.annotation or :annotation is null) ")
    public List<AnnotationAttribute> findByAnnotationAttributeIdAndPropertyAndValueAndAnnotation(
        @Param("annotationAttributeId")
        Long annotationAttributeId,
        @Param("property")
        String property,
        @Param("value")
        String value,
        @Param("annotation")
        Annotation annotation);

}
