
package it.anggen.repository.field;

import java.util.List;
import it.anggen.model.field.AnnotationAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationRepository
    extends CrudRepository<it.anggen.model.field.Annotation, java.lang.Long>
{


    public List<it.anggen.model.field.Annotation> findByAnnotationId(java.lang.Long annotationId);

    public List<it.anggen.model.field.Annotation> findByAnnotationType(java.lang.Integer annotationType);

    public List<it.anggen.model.field.Annotation> findByField(it.anggen.model.field.Field field);

    public List<it.anggen.model.field.Annotation> findByRelationship(it.anggen.model.relationship.Relationship relationship);

    public List<it.anggen.model.field.Annotation> findByEnumField(it.anggen.model.field.EnumField enumField);

    @Query("select a from Annotation a where  (:annotationId is null or cast(:annotationId as string)=cast(a.annotationId as string)) and (:annotationType is null or cast(:annotationType as string)=cast(a.annotationType as string)) and (:annotationAttribute in elements(a.annotationAttributeList)  or :annotationAttribute is null) and (:field=a.field or :field is null) and (:relationship=a.relationship or :relationship is null) and (:enumField=a.enumField or :enumField is null) ")
    public List<it.anggen.model.field.Annotation> findByAnnotationIdAndAnnotationTypeAndAnnotationAttributeAndFieldAndRelationshipAndEnumField(
        @org.springframework.data.repository.query.Param("annotationId")
        java.lang.Long annotationId,
        @org.springframework.data.repository.query.Param("annotationType")
        java.lang.Integer annotationType,
        @org.springframework.data.repository.query.Param("annotationAttribute")
        AnnotationAttribute annotationAttribute,
        @org.springframework.data.repository.query.Param("field")
        it.anggen.model.field.Field field,
        @org.springframework.data.repository.query.Param("relationship")
        it.anggen.model.relationship.Relationship relationship,
        @org.springframework.data.repository.query.Param("enumField")
        it.anggen.model.field.EnumField enumField);

}