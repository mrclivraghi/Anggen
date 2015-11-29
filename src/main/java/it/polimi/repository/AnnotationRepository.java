
package it.polimi.repository;

import java.util.List;

import it.polimi.model.field.Annotation;
import it.polimi.model.field.AnnotationAttribute;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationRepository
    extends CrudRepository<Annotation, Long>
{


    public List<Annotation> findByAnnotationId(Long annotationId);

    public List<Annotation> findByAnnotationType(Integer annotationType);

    public List<Annotation> findByField(Field field);

    public List<Annotation> findByRelationship(Relationship relationship);

    public List<Annotation> findByEnumField(EnumField enumField);

    @Query("select a from Annotation a where  (:annotationId is null or cast(:annotationId as string)=cast(a.annotationId as string)) and (:annotationType is null or cast(:annotationType as string)=cast(a.annotationType as string)) and (:annotationAttribute in elements(a.annotationAttributeList)  or :annotationAttribute is null) and (:field=a.field or :field is null) and (:relationship=a.relationship or :relationship is null) and (:enumField=a.enumField or :enumField is null) ")
    public List<Annotation> findByAnnotationIdAndAnnotationTypeAndAnnotationAttributeAndFieldAndRelationshipAndEnumField(
        @Param("annotationId")
        Long annotationId,
        @Param("annotationType")
        Integer annotationType,
        @Param("annotationAttribute")
        AnnotationAttribute annotationAttribute,
        @Param("field")
        Field field,
        @Param("relationship")
        Relationship relationship,
        @Param("enumField")
        EnumField enumField);

}
