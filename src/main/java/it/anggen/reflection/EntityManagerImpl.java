package it.anggen.reflection;

import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.model.AnnotationType;
import it.anggen.model.FieldType;
import it.anggen.model.RelationshipType;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.Tab;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;

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
		List<Entity> descendantEntityList = new ArrayList<Entity>();
		addDescendantEntities(entity, descendantEntityList,0,entity.getDescendantMaxLevel());		
		return descendantEntityList;
	}
	
	private void addDescendantEntities(Entity entity, List<Entity> descendantEntityList,Integer level,Integer maxLevel)
	{
		if (level>=maxLevel)
			return;
		for (Relationship relationship: entity.getRelationshipList())
		{
			if (!descendantEntityList.contains(relationship.getEntityTarget()) && (!relationship.getEntityTarget().equals(this.entity)))
			{
				descendantEntityList.add(relationship.getEntityTarget());
				addDescendantEntities(relationship.getEntityTarget(), descendantEntityList,level+1,maxLevel);
			}
				
		}
	}
	
	@Override
	public List<Entity> getDescendantEntities() {
		
		List<Entity> descendantEntityList = new ArrayList<Entity>();
		addDescendantEntities(entity, descendantEntityList,0,entity.getDescendantMaxLevel());		
		return descendantEntityList;
		
	}

	@Override
	public List<Field> getDescriptionField() {
		List<Field> fieldList= new ArrayList<Field>();
		for (Field field: getFieldList())
		{
			if (EntityAttributeManager.getInstance(field).getDescriptionField() || EntityAttributeManager.getInstance(field).getPrimaryKey())
				fieldList.add(field);
		}
		return fieldList;
	}

	@Override
	public FieldType getKeyClass() {
		for (Field field: getFieldList())
		{
			if (EntityAttributeManager.getInstance(field).getPrimaryKey())
				return field.getFieldType();
		}
		return null;
	}

	@Override
	public String getAllParam()
	{
		String string="";
		List<EntityAttribute> entityAttributeList = getAllAttribute();
		String className = entity.getName();
		for (EntityAttribute entityAttribute: entityAttributeList)
		{
			
			String entityAttributeName= EntityAttributeManager.getInstance(entityAttribute).asField()!=null ? entityAttribute.getName() : (EntityAttributeManager.getInstance(entityAttribute).isRelationship()? EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget().getName(): EntityAttributeManager.getInstance(entityAttribute).asEnumField().getName());
			
			if (EntityAttributeManager.getInstance(entityAttribute).getBetweenFilter())
			{
				string=string+manageSingleParam(className, entityAttribute,  entityAttributeName+"From");
				string=string+manageSingleParam(className, entityAttribute,  entityAttributeName+"To");
			}else
				string=string+manageSingleParam(className, entityAttribute,  entityAttributeName);
			
			/*if (entityAttribute.getChildrenFilterList()!=null)
				for (Field filterField: entityAttribute.getChildrenFilterList())
				{
					ReflectionManager reflectionManager = new ReflectionManager(filterField.getOwnerClass());
					String filterFieldName=reflectionManager.parseName(filterField.getOwnerClass().getName())+Utility.getFirstUpper(filterField.getName());
					string = string+manageSingleParam(className, filterField, filterFieldName);
					
				}*/
			
			
		}
		//children filter
		for (EntityAttribute filterAttribute: getChildrenFilter())
		{
			String filterFieldName=EntityAttributeManager.getInstance(filterAttribute).getParent().getName()+Utility.getFirstUpper(filterAttribute.getName());
			string = string+manageSingleParam(className, filterAttribute, filterFieldName);
		
		}
		return string.substring(0, string.length()-1);
	}
	
	private String manageSingleParam(String className,EntityAttribute entityAttribute, String fieldName)
	{
		String string="";
		if (EntityAttributeManager.getInstance(entityAttribute).asEnumField()!=null)
		{
			string=string+" ("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()==null)? null : "+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"().getValue(),";
		}else
		{
			if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.TIME)
			{
				string=string+"it.anggen.utils.Utility.formatTime("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()),";
			}
			else
			{
				if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.DATE)
				{
					string=string+"it.anggen.utils.Utility.formatDate("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()),";
				}else
				{
					if (EntityAttributeManager.getInstance(entityAttribute).isList())
						string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"List()==null? null :"+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"List().get(0),";
					else
					{
						if (EntityAttributeManager.getInstance(entityAttribute).isEnumField())
							string=string+" ("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()==null)? null : "+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"().getValue(),";
						else
						string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"(),";
					}
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
	public List<EntityAttribute> getFieldByTab(Tab tab) {
		if (tab==null || tab.getName().equals("Searchdetails")) return getAllAttribute();
		List<EntityAttribute> entityAttributeList= new ArrayList<EntityAttribute>();
		entityAttributeList.addAll(tab.getFieldList());
		entityAttributeList.addAll(tab.getRelationshipList());
		entityAttributeList.addAll(tab.getEnumFieldList());
		Utility.orderByPriority(entityAttributeList);
		return entityAttributeList;
	}

	@Override
	public Boolean hasManyToMany() {
		for (Relationship relationship : entity.getRelationshipList())
		{
			if (relationship.getRelationshipType()==RelationshipType.MANY_TO_MANY || relationship.getRelationshipType()==RelationshipType.MANY_TO_MANY_BACK)
				return true;
		}
		return false;
	}

	@Override
	public List<Tab> getTabList() {
		return entity.getTabList();
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
			
				if (EntityAttributeManager.getInstance(entityAttribute).getDescriptionField())
				{
					if (withGetter)
					{
						if (EntityAttributeManager.getInstance(entityAttribute).isRelationship())
							descriptionFields=descriptionFields+" "+entityName+".get"+Utility.getFirstUpper(entityAttribute.getName())+"().get"+Utility.getFirstUpper(entityAttribute.getName())+"Id+' '+";
						else
							descriptionFields=descriptionFields+" "+entityName+".get"+Utility.getFirstUpper(entityAttribute.getName())+"()+' '+";
						
					}
					else
					{
						if (EntityAttributeManager.getInstance(entityAttribute).isRelationship())
							descriptionFields=descriptionFields+" "+entityName+"."+entityAttribute.getName()+"."+Utility.getFirstUpper(entityAttribute.getName())+"Id+' '+";
						else	
							descriptionFields=descriptionFields+" "+entityName+"."+entityAttribute.getName()+"+' '+";
					}

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

	/**
	 * return even enumField
	 */
	@Override
	public List<EntityAttribute> getAllAttribute() {
		List<EntityAttribute> tempList = getAttributeList();
		tempList.addAll(entity.getEnumFieldList());
		if (tempList!=null)
			Utility.orderByPriority(tempList);
		return tempList;
	}

	@Override
	public List<EntityAttribute> getChildrenFilter() {
		List<EntityAttribute> childrenFilterList = new ArrayList<EntityAttribute>();
		for (Relationship relationship: entity.getRelationshipList())
		{
			EntityManager childrenEntityManager = new EntityManagerImpl(relationship.getEntityTarget());
			
			for (EntityAttribute childrenEntityAttribute: childrenEntityManager.getAllAttribute())
			{
				if (EntityAttributeManager.getInstance(childrenEntityAttribute).hasAnnotation(AnnotationType.FILTER_FIELD))
					childrenFilterList.add(childrenEntityAttribute);
			}
		}
		
		
		return childrenFilterList;
	}

	@Override
	public Boolean isLastLevel(Entity entity) {
		List<Entity> descendantEntityList = new ArrayList<Entity>();
		descendantEntityList.add(entity);
		addDescendantEntities(this.entity, descendantEntityList,0,this.entity.getDescendantMaxLevel()-1);	
		if (descendantEntityList.contains(entity))
			return false;
		return true;
	}

	@Override
	public List<Relationship> getDescendantRelationship() {
		List<Relationship> descendantRelationshipList = new ArrayList<Relationship>();
		addDescendantRelationship(entity, descendantRelationshipList,0,entity.getDescendantMaxLevel());		
		return descendantRelationshipList;
	}
	
	private void addDescendantRelationship(Entity entity, List<Relationship> descendantRelationshipList,Integer level,Integer maxLevel)
	{
		if (level>=maxLevel)
			return;
		for (Relationship relationship: entity.getRelationshipList())
		{
			if (!descendantRelationshipList.contains(relationship) && (!relationship.getEntityTarget().equals(this.entity)))
			{
				descendantRelationshipList.add(relationship);
				addDescendantRelationship(relationship.getEntityTarget(), descendantRelationshipList,level+1,maxLevel);
			}
				
		}
	}
	


}
