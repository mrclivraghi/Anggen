package it.polimi.model.domain;

public enum RelationshipType {
	ONE_TO_ONE(0),
	ONE_TO_MANY(1),
	MANY_TO_ONE(2),
	MANY_TO_MANY(3),
	MANY_TO_MANY_BACK(4);
	
	private final int value;
	
	private RelationshipType(int value)
	{
		this.value=value;
	}
	
	public int getValue()
	{
		return this.value;
	}
}
