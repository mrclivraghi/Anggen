package it.polimi.reflection;

public interface FieldManager {
	public Boolean isKnownClass();
	public Boolean isDateField();
	public Boolean isTimeField();
	public Boolean hasAnnotation();
	public Boolean isPrimaryKey();
}
