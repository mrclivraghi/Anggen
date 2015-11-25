package it.polimi.model.domain;

public enum RestrictionType {
	SEARCH(0),
	UPDATE(1),
	INSERT(2),
	DELETE(3);
	
	private final int value;
	
	private RestrictionType(int value)
	{
		this.value=value;
	}
	
	public int getValue()
	{
		return this.value;
	}
}
