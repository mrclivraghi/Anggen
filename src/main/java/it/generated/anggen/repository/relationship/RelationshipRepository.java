
package it.generated.anggen.repository.relationship;

import java.util.List;
import it.generated.anggen.model.field.Annotation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository
    extends CrudRepository<it.generated.anggen.model.relationship.Relationship, java.lang.Long>
{


    public List<it.generated.anggen.model.relationship.Relationship> findByName(java.lang.String name);

    public List<it.generated.anggen.model.relationship.Relationship> findByRelationshipId(java.lang.Long relationshipId);

    public List<it.generated.anggen.model.relationship.Relationship> findByTab(it.generated.anggen.model.entity.Tab tab);

    public List<it.generated.anggen.model.relationship.Relationship> findByEntityTarget(it.generated.anggen.model.entity.Entity entityTarget);

    public List<it.generated.anggen.model.relationship.Relationship> findByEntity(it.generated.anggen.model.entity.Entity entity);

    public List<it.generated.anggen.model.relationship.Relationship> findByRelationshipType(java.lang.Integer relationshipType);

    @Query("select r from Relationship r where  (:name is null or :name='' or cast(:name as string)=r.name) and (:relationshipId is null or cast(:relationshipId as string)=cast(r.relationshipId as string)) and (:tab=r.tab or :tab is null) and (:annotation in elements(r.annotationList)  or :annotation is null) and (:entityTarget=r.entityTarget or :entityTarget is null) and (:entity=r.entity or :entity is null) and (:relationshipType is null or cast(:relationshipType as string)=cast(r.relationshipType as string)) ")
    public List<it.generated.anggen.model.relationship.Relationship> findByNameAndRelationshipIdAndTabAndAnnotationAndEntityAndEntityAndRelationshipType(
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("relationshipId")
        java.lang.Long relationshipId,
        @org.springframework.data.repository.query.Param("tab")
        it.generated.anggen.model.entity.Tab tab,
        @org.springframework.data.repository.query.Param("annotation")
        Annotation annotation,
        @org.springframework.data.repository.query.Param("entityTarget")
        it.generated.anggen.model.entity.Entity entityTarget,
        @org.springframework.data.repository.query.Param("entity")
        it.generated.anggen.model.entity.Entity entity,
        @org.springframework.data.repository.query.Param("relationshipType")
        java.lang.Integer relationshipType);

}
