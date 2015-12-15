package it.anggen.reflection;

import it.anggen.utils.EntityAttribute;
import it.anggen.model.FieldType;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.Tab;
import it.anggen.model.field.Field;

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
	public Boolean isLastLevel(Entity entity);
}
