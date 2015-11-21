package it.polimi.model.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

public class EntityAttribute {

	
	
	//bad, violating encapsulating
	public void setName(String name)
	{
		if (isField())
			this.asField().setName(name);
		else
			this.asRelationship().setName(name);
	}
	
	public String getName()
	{
		if (isField())
			return this.asField().getName();
		return this.asRelationship().getEntityTarget().getName();
	}
	
	public Boolean getIgnoreSearch() {
		if (isField())
			return this.asField().getIgnoreSearch();
		return this.asRelationship().getIgnoreSearch();
	}

	public Boolean getIgnoreUpdate() {
		if (isField())
			return this.asField().getIgnoreUpdate();
		return this.asRelationship().getIgnoreUpdate();
	}

	public Boolean getBetweenFilter() {
		if (isField())
			return this.asField().getBetweenFilter();
		return this.asRelationship().getBetweenFilter();
	}
	
	
	
	public Boolean isField()
	{
		return this instanceof Field;
	}
	
	public Boolean isRelationship()
	{
		return this instanceof Relationship;
	}
	
	public Relationship asRelationship()
	{
		if (isRelationship())
			return (Relationship) this;
		else
			return null;
	}
	public Field asField()
	{
		if (isField())
			return (Field) this;
		else 
			return null;
	}

	

}
