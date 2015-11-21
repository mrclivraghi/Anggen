package it.polimi.reflection;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityAttribute;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.FieldType;
import it.polimi.model.domain.Relationship;
import it.polimi.model.domain.RelationshipType;

import java.util.ArrayList;
import java.util.List;

public class EntityManagerImpl implements EntityManager{

	private Entity entity;
	
	public EntityManagerImpl(Entity entity) {
		this.entity=entity;
	}

	@Override
	public List<Field> getFieldList() {
		return entity.getFieldList();
	}

	@Override
	public Boolean hasList() {
		return (entity.getRelationshipList()!=null && entity.getRelationshipList().size()>0);
	}

	@Override
	public List<Entity> getChildrenEntities() {
		List<Entity> entityList= new ArrayList<Entity>();
		for (Relationship relationship: entity.getRelationshipList())
		{
			entityList.add(relationship.getEntityTarget());
		}
		return entityList;
	}

	
	private List<Entity> getDescendantEntities(Entity entity, List<Entity> parentEntities)
	{
		List<Entity> descendantEntities = new ArrayList<Entity>();
		for (Relationship relationship: entity.getRelationshipList())
		{
			if (!parentEntities.contains(relationship.getEntityTarget()))
			{
				descendantEntities.add(relationship.getEntityTarget());
			}
		}
		
		return descendantEntities;
	}
	
	@Override
	public List<Entity> getDescendantEntities() {

		List<Entity> parentEntity = new ArrayList<Entity>();
		List<Entity> descendantEntity = new ArrayList<Entity>();
		parentEntity.add(entity);
		for (Relationship relationship: entity.getRelationshipList())
		{
			if (!parentEntity.contains(relationship.getEntityTarget()))
			{
				descendantEntity.add(relationship.getEntityTarget());
			}
		}
		for (Relationship relationship : entity.getRelationshipList())
		{
			if (!parentEntity.contains(relationship.getEntityTarget()))
			{
				parentEntity.add(relationship.getEntityTarget());
				descendantEntity.addAll(getDescendantEntities(relationship.getEntityTarget(),parentEntity));
			}
		}
		
		
		return descendantEntity;
	}

	@Override
	public List<Field> getDescriptionField() {
		List<Field> fieldList= new ArrayList<Field>();
		for (Field field: getFieldList())
		{
			if (field.getDescriptionField())
				fieldList.add(field);
		}
		return fieldList;
	}

	@Override
	public FieldType getKeyClass() {
		for (Field field: getFieldList())
		{
			if (field.getPrimaryKey())
				return field.getFieldType();
		}
		return null;
	}

	@Override
	public String getAllParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean containFieldOfEntity(Entity targetEntity) {
		for (Relationship relationship: entity.getRelationshipList())
		{
			if (relationship.getEntityTarget().getEntityId()==targetEntity.getEntityId())
				return true;
		}
		return false;
	}

	@Override
	public List<Field> getFieldByTab() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasManyToMany() {
		for (Relationship relationship : entity.getRelationshipList())
		{
			if (relationship.getRelationshipType()==RelationshipType.MANY_TO_MANY)
				return true;
		}
		return false;
	}

	@Override
	public List<String> getTabsName() {
		List<String> tabNameList = new ArrayList<String>();
		tabNameList.add("Default");
		return tabNameList;
	}

	@Override
	public List<EntityAttribute> getAttributeList() {
		List<EntityAttribute> entityAttributeList = new ArrayList<EntityAttribute>();
		for (Field field: getFieldList())
		{
			entityAttributeList.add(field);
		}
		for (Relationship relationship : entity.getRelationshipList())
		{
			entityAttributeList.add(relationship);
		}
		return entityAttributeList;
	}

}
