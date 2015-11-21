package it.polimi.model.domain;

public enum FieldType {
	STRING(0),
	INTEGER(1),
	DATE(2),
	DOUBLE(3),
	TIME(4),
	BOOLEAN(5),
	ENUM(6);
	
	private final int value;
	
	private FieldType(int value)
	{
		this.value=value;
	}
	
	public int getValue()
	{
		return this.value;
	}
}
