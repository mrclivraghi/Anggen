
package it.polimi.service.relationship;

import java.util.List;

import it.polimi.repository.field.AnnotationRepository;
import it.polimi.repository.relationship.RelationshipRepository;
import it.polimi.searchbean.relationship.RelationshipSearchBean;
import it.polimi.service.relationship.RelationshipService;

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
    public List<it.polimi.model.relationship.Relationship> findById(Long relationshipId) {
        return relationshipRepository.findByRelationshipId(relationshipId);
    }

    @Override
    public List<it.polimi.model.relationship.Relationship> find(RelationshipSearchBean relationship) {
        return relationshipRepository.findByRelationshipIdAndNameAndEntityAndEntityAndAnnotationAndTabAndRelationshipType(relationship.getRelationshipId(),relationship.getName(),relationship.getEntity(),relationship.getEntity(),relationship.getAnnotationList()==null? null :relationship.getAnnotationList().get(0),relationship.getTab(), (relationship.getRelationshipType()==null)? null : relationship.getRelationshipType().getValue());
    }

    @Override
    public void deleteById(Long relationshipId) {
        relationshipRepository.delete(relationshipId);
        return;
    }

    @Override
    public it.polimi.model.relationship.Relationship insert(it.polimi.model.relationship.Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    @Transactional
    public it.polimi.model.relationship.Relationship update(it.polimi.model.relationship.Relationship relationship) {
        if (relationship.getAnnotationList()!=null)
        for (it.polimi.model.field.Annotation annotation: relationship.getAnnotationList())
        {
        annotation.setRelationship(relationship);
        }
        it.polimi.model.relationship.Relationship returnedRelationship=relationshipRepository.save(relationship);
        if (relationship.getEntity()!=null)
        {
        List<it.polimi.model.relationship.Relationship> relationshipList = relationshipRepository.findByEntity( relationship.getEntity());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getEntity().setRelationshipList(relationshipList);
        }
        if (relationship.getEntity()!=null)
        {
        List<it.polimi.model.relationship.Relationship> relationshipList = relationshipRepository.findByEntity( relationship.getEntity());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getEntity().setRelationshipList(relationshipList);
        }
        if (relationship.getTab()!=null)
        {
        List<it.polimi.model.relationship.Relationship> relationshipList = relationshipRepository.findByTab( relationship.getTab());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getTab().setRelationshipList(relationshipList);
        }
         return returnedRelationship;
    }

}
