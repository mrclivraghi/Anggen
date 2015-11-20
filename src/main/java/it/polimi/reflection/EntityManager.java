package it.polimi.reflection;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.FieldType;

import java.util.List;

public interface EntityManager {
	public List<Field> getFieldList();
	public Boolean hasList();
	public List<Entity> getChildrenEntities();
	public List<Entity> getDescendantEntities();
	public List<Field> getDescriptionField();
	public FieldType getKeyClass();
	public String getAllParam();
	public Boolean containFieldOfEntity(Entity targetEntity);
	public List<Field> getFieldByTab();
}
