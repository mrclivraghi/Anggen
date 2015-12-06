
package it.polimi.service.relationship;

import java.util.List;

import it.polimi.searchbean.relationship.RelationshipSearchBean;

public interface RelationshipService {


    public List<it.polimi.model.relationship.Relationship> findById(Long RelationshipId);

    public List<it.polimi.model.relationship.Relationship> find(RelationshipSearchBean Relationship);

    public void deleteById(Long RelationshipId);

    public it.polimi.model.relationship.Relationship insert(it.polimi.model.relationship.Relationship Relationship);

    public it.polimi.model.relationship.Relationship update(it.polimi.model.relationship.Relationship Relationship);

}
