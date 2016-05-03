
package it.anggen.repository.relationship;

import java.util.List;
import it.anggen.model.field.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository
    extends JpaRepository<it.anggen.model.relationship.Relationship, java.lang.Long>
{


    @Query("select r from Relationship r")
    public List<it.anggen.model.relationship.Relationship> findAll();

    public List<it.anggen.model.relationship.Relationship> findByRelationshipId(java.lang.Long relationshipId);

    public List<it.anggen.model.relationship.Relationship> findByPriority(java.lang.Integer priority);

    public List<it.anggen.model.relationship.Relationship> findByName(java.lang.String name);

    public List<it.anggen.model.relationship.Relationship> findByRelationshipType(java.lang.Integer relationshipType);

    public List<it.anggen.model.relationship.Relationship> findByEntityTarget(it.anggen.model.entity.Entity entityTarget);

    public List<it.anggen.model.relationship.Relationship> findByEntity(it.anggen.model.entity.Entity entity);

    public List<it.anggen.model.relationship.Relationship> findByTab(it.anggen.model.entity.Tab tab);

    @Query("select r from Relationship r where  (:relationshipId is null or cast(:relationshipId as string)=cast(r.relationshipId as string)) and (:priority is null or cast(:priority as string)=cast(r.priority as string)) and (:name is null or :name='' or cast(:name as string)=r.name) and (:relationshipType is null or cast(:relationshipType as string)=cast(r.relationshipType as string)) and (:annotation in elements(r.annotationList)  or :annotation is null) and (:entityTarget=r.entityTarget or :entityTarget is null) and (:entity=r.entity or :entity is null) and (:tab=r.tab or :tab is null) ")
    public List<it.anggen.model.relationship.Relationship> findByRelationshipIdAndPriorityAndNameAndRelationshipTypeAndAnnotationAndEntityTargetAndEntityAndTab(
        @org.springframework.data.repository.query.Param("relationshipId")
        java.lang.Long relationshipId,
        @org.springframework.data.repository.query.Param("priority")
        java.lang.Integer priority,
        @org.springframework.data.repository.query.Param("name")
        java.lang.String name,
        @org.springframework.data.repository.query.Param("relationshipType")
        java.lang.Integer relationshipType,
        @org.springframework.data.repository.query.Param("annotation")
        Annotation annotation,
        @org.springframework.data.repository.query.Param("entityTarget")
        it.anggen.model.entity.Entity entityTarget,
        @org.springframework.data.repository.query.Param("entity")
        it.anggen.model.entity.Entity entity,
        @org.springframework.data.repository.query.Param("tab")
        it.anggen.model.entity.Tab tab);

}
