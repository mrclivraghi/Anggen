package it.polimi.model.field;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum FieldType {
	STRING(0),
	INTEGER(1),
	DATE(2),
	DOUBLE(3),
	TIME(4),
	BOOLEAN(5),
	LONG(6),
	FILE(7),
	PHOTO(8);
	
	private final int value;
	
	private FieldType(int value)
	{
		this.value=value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	@JsonIgnore
	public String getName()
	{
		switch (this.value)
		{
		case 0: return "String";
		case 1: return "Integer";
		case 2: return "Date";
		case 3: return "Double";
		case 4: return "Time";
		case 5: return "Boolean";
		case 6: return "Long";
		case 7: return "String";
		case 8: return "String";
		
		}
		return null;
	}
	
	@JsonIgnore
	public Class getFieldClass()
	{
		switch (this.value)
		{
		case 0: return String.class;
		case 1: return Integer.class;
		case 2: return Date.class;
		case 3: return Double.class;
		case 4: return Time.class;
		case 5: return Boolean.class;
		case 6: return Long.class;
		case 7: return String.class;
		case 8: return String.class;
		}
		return null;
	}
	
}