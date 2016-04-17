
package it.anggen.service.relationship;

import java.util.List;
import it.anggen.repository.field.AnnotationRepository;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.searchbean.relationship.RelationshipSearchBean;
import it.anggen.service.relationship.RelationshipService;
import org.springframework.data.domain.Page;
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
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.relationship.Relationship> findById(Long relationshipId) {
        return relationshipRepository.findByRelationshipId(relationshipId);
    }

    @Override
    public Page<it.anggen.model.relationship.Relationship> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "relationshipId");
        return relationshipRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.relationship.Relationship> find(RelationshipSearchBean relationship) {
        return relationshipRepository.findByRelationshipIdAndNameAndPriorityAndRelationshipTypeAndAnnotationAndEntityAndEntityAndTab(relationship.getRelationshipId(),relationship.getName(),relationship.getPriority(), (relationship.getRelationshipType()==null)? null : relationship.getRelationshipType().getValue(),relationship.getAnnotationList()==null? null :relationship.getAnnotationList().get(0),relationship.getEntity(),relationship.getEntity(),relationship.getTab());
    }

    @Override
    public void deleteById(Long relationshipId) {
        relationshipRepository.delete(relationshipId);
        return;
    }

    @Override
    public it.anggen.model.relationship.Relationship insert(it.anggen.model.relationship.Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    @Transactional
    public it.anggen.model.relationship.Relationship update(it.anggen.model.relationship.Relationship relationship) {
        if (relationship.getAnnotationList()!=null)
        for (it.anggen.model.field.Annotation annotation: relationship.getAnnotationList())
        {
        annotation.setRelationship(relationship);
        }
        it.anggen.model.relationship.Relationship returnedRelationship=relationshipRepository.save(relationship);
        if (relationship.getEntity()!=null)
        {
        List<it.anggen.model.relationship.Relationship> relationshipList = relationshipRepository.findByEntity( relationship.getEntity());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getEntity().setRelationshipList(relationshipList);
        }
        if (relationship.getEntity()!=null)
        {
        List<it.anggen.model.relationship.Relationship> relationshipList = relationshipRepository.findByEntity( relationship.getEntity());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getEntity().setRelationshipList(relationshipList);
        }
        if (relationship.getTab()!=null)
        {
        List<it.anggen.model.relationship.Relationship> relationshipList = relationshipRepository.findByTab( relationship.getTab());
        if (!relationshipList.contains(returnedRelationship))
        relationshipList.add(returnedRelationship);
        returnedRelationship.getTab().setRelationshipList(relationshipList);
        }
         return returnedRelationship;
    }

}
