
package it.anggen.service.relationship;

import java.util.List;
import it.anggen.searchbean.relationship.RelationshipSearchBean;

public interface RelationshipService {


    public List<it.anggen.model.relationship.Relationship> findById(Long RelationshipId);

    public List<it.anggen.model.relationship.Relationship> find(RelationshipSearchBean Relationship);

    public void deleteById(Long RelationshipId);

    public it.anggen.model.relationship.Relationship insert(it.anggen.model.relationship.Relationship Relationship);

    public it.anggen.model.relationship.Relationship update(it.anggen.model.relationship.Relationship Relationship);

}
