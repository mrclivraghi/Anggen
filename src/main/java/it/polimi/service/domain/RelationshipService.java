
package it.polimi.service.domain;

import java.util.List;
import it.polimi.model.domain.Relationship;
import it.polimi.searchbean.domain.RelationshipSearchBean;

public interface RelationshipService {


    public List<Relationship> findById(Long relationshipId);

    public List<Relationship> find(RelationshipSearchBean relationship);

    public void deleteById(Long relationshipId);

    public Relationship insert(Relationship relationship);

    public Relationship update(Relationship relationship);

}
