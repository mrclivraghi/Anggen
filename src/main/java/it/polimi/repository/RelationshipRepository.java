
package it.polimi.repository;

import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Relationship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository
    extends CrudRepository<Relationship, Long>
{


    public List<Relationship> findByRelationshipId(Long relationshipId);

    public List<Relationship> findByName(String name);

    public List<Relationship> findByEntity(Entity entity);

    public List<Relationship> findByEntityTarget(Entity entityTarget);

    public List<Relationship> findByRelationshipType(Integer relationshipType);

    @Query("select r from Relationship r where  (:relationshipId is null or cast(:relationshipId as string)=cast(r.relationshipId as string)) and (:name is null or :name='' or cast(:name as string)=r.name) and (:entity=r.entity or :entity is null) and (:entityTarget=r.entityTarget or :entityTarget is null) and (:relationshipType is null or cast(:relationshipType as string)=cast(r.relationshipType as string)) and (:annotation in elements(r.annotationList)  or :annotation is null) ")
    public List<Relationship> findByRelationshipIdAndNameAndEntityAndEntityTargetAndRelationshipTypeAndAnnotation(
        @Param("relationshipId")
        Long relationshipId,
        @Param("name")
        String name,
        @Param("entity")
        Entity entity,
        @Param("entityTarget")
        Entity entityTarget,
        @Param("relationshipType")
        Integer relationshipType,
        @Param("annotation")
        Annotation annotation);

}
