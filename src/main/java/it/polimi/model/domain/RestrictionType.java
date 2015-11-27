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
	
	@Override
	public String toString()
	{
		switch (this)
		{
		case DELETE:	return "DELETE";
		case INSERT:	return "INSERT";
		case SEARCH:	return "SEARCH";
		case UPDATE:	return "UPDATE";
		default:	return null;
		}
	}
}
