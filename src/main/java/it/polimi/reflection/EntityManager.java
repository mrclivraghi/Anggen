package it.polimi.reflection;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityAttribute;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.FieldType;

import java.util.List;

public interface EntityManager {
	public List<EntityAttribute> getAttributeList();
	public List<Field> getFieldList();
	public Boolean hasList();
	public List<Entity> getChildrenEntities();
	public List<Entity> getDescendantEntities();
	public List<Entity> getDescendantEntities(Entity entity, List<Entity> parentEntities);
	public List<Field> getDescriptionField();
	public FieldType getKeyClass();
	public String getAllParam();
	public Boolean containFieldOfEntity(Entity targetEntity);
	public List<EntityAttribute> getFieldByTab();
	public Boolean hasManyToMany();
	public List<String> getTabsName();
	public String getDescription();
	public String getDescription(Boolean withGetter);
	public String getDescription(Boolean withGetter, String fieldName);
	public List<EntityAttribute> getAllAttribute();
	public List<EntityAttribute> getChildrenFilter();
}
