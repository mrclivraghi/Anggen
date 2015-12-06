package it.polimi.reflection;

import it.polimi.model.EntityAttribute;
import it.polimi.model.FieldType;
import it.polimi.model.entity.Entity;
import it.polimi.model.entity.Tab;
import it.polimi.model.field.Field;

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
	public List<EntityAttribute> getFieldByTab(Tab tab);
	public List<Tab> getTabList();
	public Boolean hasManyToMany();
	public String getDescription();
	public String getDescription(Boolean withGetter);
	public String getDescription(Boolean withGetter, String fieldName);
	public List<EntityAttribute> getAllAttribute();
	public List<EntityAttribute> getChildrenFilter();
}
