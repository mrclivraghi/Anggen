package it.polimi.reflection;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.FieldType;
import it.polimi.model.domain.Relationship;

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

	@Override
	public List<Entity> getDescendantEntities() {
		// TODO Auto-generated method stub
		return null;
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

}
