
package it.polimi.service;

import java.util.List;

import it.polimi.model.relationship.Relationship;
import it.polimi.searchbean.RelationshipSearchBean;

public interface RelationshipService {


    public List<Relationship> findById(Long relationshipId);

    public List<Relationship> find(RelationshipSearchBean relationship);

    public void deleteById(Long relationshipId);

    public Relationship insert(Relationship relationship);

    public Relationship update(Relationship relationship);

}
