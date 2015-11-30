
package it.generated.anggen.repository.field;

import java.util.List;
import it.generated.anggen.model.field.AnnotationAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationRepository
    extends CrudRepository<it.generated.anggen.model.field.Annotation, java.lang.Long>
{


    public List<it.generated.anggen.model.field.Annotation> findByAnnotationId(java.lang.Long annotationId);

    public List<it.generated.anggen.model.field.Annotation> findByEnumField(it.generated.anggen.model.field.EnumField enumField);

    public List<it.generated.anggen.model.field.Annotation> findByRelationship(it.generated.anggen.model.relationship.Relationship relationship);

    public List<it.generated.anggen.model.field.Annotation> findByField(it.generated.anggen.model.field.Field field);

    public List<it.generated.anggen.model.field.Annotation> findByAnnotationType(java.lang.Integer annotationType);

    @Query("select a from Annotation a where  (:annotationId is null or cast(:annotationId as string)=cast(a.annotationId as string)) and (:enumField=a.enumField or :enumField is null) and (:relationship=a.relationship or :relationship is null) and (:field=a.field or :field is null) and (:annotationAttribute in elements(a.annotationAttributeList)  or :annotationAttribute is null) and (:annotationType is null or cast(:annotationType as string)=cast(a.annotationType as string)) ")
    public List<it.generated.anggen.model.field.Annotation> findByAnnotationIdAndEnumFieldAndRelationshipAndFieldAndAnnotationAttributeAndAnnotationType(
        @org.springframework.data.repository.query.Param("annotationId")
        java.lang.Long annotationId,
        @org.springframework.data.repository.query.Param("enumField")
        it.generated.anggen.model.field.EnumField enumField,
        @org.springframework.data.repository.query.Param("relationship")
        it.generated.anggen.model.relationship.Relationship relationship,
        @org.springframework.data.repository.query.Param("field")
        it.generated.anggen.model.field.Field field,
        @org.springframework.data.repository.query.Param("annotationAttribute")
        AnnotationAttribute annotationAttribute,
        @org.springframework.data.repository.query.Param("annotationType")
        java.lang.Integer annotationType);

}
