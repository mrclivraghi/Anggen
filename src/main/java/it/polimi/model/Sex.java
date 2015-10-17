package it.polimi.model;

public enum Sex {
	MALE(0),
	FEMALE(1);
	
	private final int value;
	
	private Sex(int value)
	{
		this.value=value;
	}
	
	public int getValue()
	{
		return this.value;
	}
}
