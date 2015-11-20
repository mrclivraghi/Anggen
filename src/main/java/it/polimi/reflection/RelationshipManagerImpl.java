package it.polimi.reflection;

import it.polimi.model.domain.Relationship;
import it.polimi.model.domain.RelationshipType;

public class RelationshipManagerImpl implements RelationshipManager {

	private Relationship relationship;
	
	public RelationshipManagerImpl(Relationship relationship) {
		this.relationship=relationship;
	}
	
	
	
	@Override
	public Boolean isManyToMany() {
		return relationship.getRelationshipType()==RelationshipType.MANY_TO_MANY;
	}

}
