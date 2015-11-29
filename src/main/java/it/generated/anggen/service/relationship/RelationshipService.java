
package it.generated.anggen.service.relationship;

import java.util.List;
import it.generated.anggen.searchbean.relationship.RelationshipSearchBean;

public interface RelationshipService {


    public List<it.generated.anggen.model.relationship.Relationship> findById(Long RelationshipId);

    public List<it.generated.anggen.model.relationship.Relationship> find(RelationshipSearchBean Relationship);

    public void deleteById(Long RelationshipId);

    public it.generated.anggen.model.relationship.Relationship insert(it.generated.anggen.model.relationship.Relationship Relationship);

    public it.generated.anggen.model.relationship.Relationship update(it.generated.anggen.model.relationship.Relationship Relationship);

}
