
package it.polimi.service;

import java.util.List;

import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Relationship;
import it.polimi.repository.AnnotationRepository;
import it.polimi.repository.RelationshipRepository;
import it.polimi.searchbean.RelationshipSearchBean;
import it.polimi.service.RelationshipService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelationshipServiceImpl
    implements RelationshipService
{

    @org.springframework.beans.factory.annotation.Autowired
    public RelationshipRepository relationshipRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public AnnotationRepository annotationRepository;

    @Override
    public List<Relationship> findById(Long relationshipId) {
        return relationshipRepository.findByRelationshipId(relationshipId);
    }

    @Override
    public List<Relationship> find(RelationshipSearchBean relationship) {
        return relationshipRepository.findByRelationshipIdAndNameAndEntityAndEntityTargetAndRelationshipTypeAndAnnotation(relationship.getRelationshipId(),relationship.getName(),relationship.getEntity(),relationship.getEntityTarget(), (relationship.getRelationshipType()==null)? null : relationship.getRelationshipType().getValue(),relationship.getAnnotationList()==null? null :relationship.getAnnotationList().get(0));
    }

    @Override
    public void deleteById(Long relationshipId) {
        relationshipRepository.delete(relationshipId);
        return;
    }

    @Override
    public Relationship insert(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    @Transactional
    public Relationship update(Relationship relationship) {
        if (relationship.getAnnotationList()!=null)
        for (Annotation annotation: relationship.getAnnotationList())
        {
        annotation.setRelationship(relationship);
        }
        Relationship returnedRelationship=relationshipRepository.save(relationship);
        if (relationship.getEntity()!=null)
        {
        List<Relationship> relationshipList = relationshipRepository.findByEntity( relationship.getEntity());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getEntity().setRelationshipList(relationshipList);
        }
        if (relationship.getEntityTarget()!=null)
        {
        List<Relationship> relationshipList = relationshipRepository.findByEntityTarget( relationship.getEntityTarget());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getEntityTarget().setRelationshipList(relationshipList);
        }
         return returnedRelationship;
    }

}
