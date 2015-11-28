package it.polimi.model.domain;

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
	LONG(6);
	
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
		}
		return "String";
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
		}
		return String.class;
	}
	
}
