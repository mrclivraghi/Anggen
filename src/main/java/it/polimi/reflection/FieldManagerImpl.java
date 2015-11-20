package it.polimi.reflection;

import it.polimi.model.domain.Field;
import it.polimi.model.domain.FieldType;

public class FieldManagerImpl implements FieldManager {

	private Field field;
	
	public FieldManagerImpl(Field field) {
		this.field=field;
	}

	@Override
	public Boolean isKnownClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isDateField() {
		return field.getFieldType()==FieldType.DATE;
	}

	@Override
	public Boolean isTimeField() {
		return field.getFieldType()==FieldType.TIME;
	}

	@Override
	public Boolean hasAnnotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isPrimaryKey() {
		return field.getPrimaryKey();
	}

}
