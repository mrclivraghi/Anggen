package it.polimi.reflection;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityAttribute;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.FieldType;
import it.polimi.model.domain.Relationship;
import it.polimi.model.domain.RelationshipType;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;
import it.polimi.utils.annotation.DescriptionField;

import java.lang.annotation.Annotation;
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
	public List<Entity> getDescendantEntities(Entity entity, List<Entity> parentEntities)
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
	public String getAllParam()
	{
		String string="";
		List<EntityAttribute> entityAttributeList = getAttributeList();
		String className = entity.getName();
		for (EntityAttribute entityAttribute: entityAttributeList)
		{
			
			String entityAttributeName= entityAttribute.asField()!=null ? entityAttribute.getName() : entityAttribute.asRelationship().getEntityTarget().getName();
			
			if (entityAttribute.getBetweenFilter())
			{
				string=string+manageSingleParam(className, entityAttribute,  entityAttributeName+"From");
				string=string+manageSingleParam(className, entityAttribute,  entityAttributeName+"To");
			}else
				string=string+manageSingleParam(className, entityAttribute,  entityAttributeName);
			
			/*addChildrenFilter(entityAttribute);
			if (entityAttribute.getChildrenFilterList()!=null)
				for (Field filterField: entityAttribute.getChildrenFilterList())
				{
					ReflectionManager reflectionManager = new ReflectionManager(filterField.getOwnerClass());
					String filterFieldName=reflectionManager.parseName(filterField.getOwnerClass().getName())+Utility.getFirstUpper(filterField.getName());
					string = string+manageSingleParam(className, filterField, filterFieldName);
					
				}
			*/
		}
		return string.substring(0, string.length()-1);
	}
	
	private String manageSingleParam(String className,EntityAttribute entityAttribute, String fieldName)
	{
		String string="";
		if (entityAttribute.asField()!=null && entityAttribute.asField().getFieldType()==FieldType.ENUM)
		{
			string=string+" ("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()==null)? null : "+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"().getValue(),";
		}else
		{
			if (entityAttribute.asField()!=null && entityAttribute.asField().getFieldType()==FieldType.TIME)
			{
				string=string+"it.polimi.utils.Utility.formatTime("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()),";
			}
			else
			{
				if (entityAttribute.asField()!=null && entityAttribute.asField().getFieldType()==FieldType.DATE)
				{
					string=string+"it.polimi.utils.Utility.formatDate("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()),";
				}else
				{
					if (entityAttribute.asRelationship()!=null && entityAttribute.asRelationship().isList())
						string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"List()==null? null :"+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"List().get(0),";
					else
						string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"(),";
				}
			}}
		return string;
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
	public List<EntityAttribute> getFieldByTab() {
		return getAttributeList();
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

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return getDescription(false);
	}

	@Override
	public String getDescription(Boolean withGetter) {
		// TODO Auto-generated method stub
		return getDescription(withGetter, entity.getName());
	}

	@Override
	public String getDescription(Boolean withGetter, String fieldName) {

		String descriptionFields="";
		String entityName=entity.getName();
		List<EntityAttribute> entityAttributeList = getAttributeList();
		
		for (EntityAttribute entityAttribute: entityAttributeList)
		{
				if (entityAttribute.getDescriptionField())
				{
					if (withGetter)
						descriptionFields=descriptionFields+" "+entityName+".get"+Utility.getFirstUpper(entityAttribute.getName())+"()+' '+";
					else
						descriptionFields=descriptionFields+" "+entityName+"."+entityAttribute.getName()+"+' '+";

				}
		}
		if (descriptionFields.length()>5)
			descriptionFields=descriptionFields.substring(0, descriptionFields.length()-5);
		else
		{
			if (withGetter)
				descriptionFields=entityName+".toString()";
			else
				descriptionFields=entityName+"."+entityName+"Id";
		}
		return descriptionFields;
	
	}

}