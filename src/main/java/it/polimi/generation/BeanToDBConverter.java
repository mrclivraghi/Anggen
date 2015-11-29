package it.polimi.generation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.EntityAttribute;
import it.polimi.model.entity.EntityGroup;
import it.polimi.model.entity.Project;
import it.polimi.model.field.AnnotationAttribute;
import it.polimi.model.field.AnnotationType;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.EnumValue;
import it.polimi.model.field.Field;
import it.polimi.model.field.FieldType;
import it.polimi.model.relationship.Relationship;
import it.polimi.model.relationship.RelationshipType;
import it.polimi.repository.AnnotationAttributeRepository;
import it.polimi.repository.AnnotationRepository;
import it.polimi.repository.EntityGroupRepository;
import it.polimi.repository.EntityRepository;
import it.polimi.repository.EnumFieldRepository;
import it.polimi.repository.EnumValueRepository;
import it.polimi.repository.FieldRepository;
import it.polimi.repository.ProjectRepository;
import it.polimi.repository.RelationshipRepository;
import it.polimi.repository.TabRepository;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.annotation.Between;
import it.polimi.utils.annotation.DescriptionField;
import it.polimi.utils.annotation.ExcelExport;
import it.polimi.utils.annotation.Filter;
import it.polimi.utils.annotation.IgnoreSearch;
import it.polimi.utils.annotation.IgnoreTableList;
import it.polimi.utils.annotation.IgnoreUpdate;
import it.polimi.utils.annotation.Tab;

import org.hibernate.type.MetaType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BeanToDBConverter {

	@Value("${application.name}")
	private String projectName;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EntityGroupRepository entityGroupRepository;
	
	@Autowired
	EntityRepository entityRepository;
	
	@Autowired
	TabRepository tabRepository;
	
	@Autowired
	FieldRepository fieldRepository;
	
	@Autowired
	AnnotationRepository annotationRepository;
	
	@Autowired
	AnnotationAttributeRepository annotationAttributeRepository;
	
	@Autowired
	RelationshipRepository relationshipRepository;
	
	@Autowired
	EnumFieldRepository enumFieldRepository;
	
	@Autowired
	EnumValueRepository enumValueRepository;
	
	
	public BeanToDBConverter() {
		
		
	}
	
	public void convert(String mainPackage)
	{
		List<String> packageList= ReflectionManager.getSubPackages(mainPackage);
		Map<String,Entity> entityMap = new HashMap<String,Entity>();
		Set<Class<?>> mainPackageClassSet = ReflectionManager.getClassInPackage(mainPackage);
		// init entities
		for (Class myClass: mainPackageClassSet)
		{
			ReflectionManager reflectionManager = new ReflectionManager(myClass);
			Entity entity = new Entity();
			entity.setName(reflectionManager.parseName());
			entityRepository.save(entity);
			entityMap.put(reflectionManager.parseName(), entity);
		}
		
		Project project = new Project();
		ReflectionManager temp = new ReflectionManager(Object.class);
		project.setName(temp.parseName(projectName));
		projectRepository.save(project);
		
		for (String myPackage: packageList)
		{
				EntityGroup entityGroup= new EntityGroup();
				entityGroup.setName(temp.parseName(myPackage));
				//entityGroupRepository.save(entityGroup);
				entityGroupRepository.save(entityGroup);
				Set<Class<?>> packageClassSet = ReflectionManager.getClassInPackage(myPackage);
				List<Entity> entityList = new ArrayList<Entity>();
				for (Class myClass: packageClassSet)
				{
					ReflectionManager reflectionManager = new ReflectionManager(myClass);
					
					Entity entity = entityMap.get(reflectionManager.parseName());
					entity.setName(reflectionManager.parseName());
					entity.setEntityGroup(entityGroup);
					//entityRepository.save(entity);
					entityRepository.save(entity);
					
					List<String> tabNameList=reflectionManager.getTabsName();
					List<it.polimi.model.entity.Tab> tabList = new ArrayList<it.polimi.model.entity.Tab>();
					for (String tabName: tabNameList)
					{
						it.polimi.model.entity.Tab metaTab = new it.polimi.model.entity.Tab();
						metaTab.setName(tabName);
						metaTab.setEntity(entity);
						tabRepository.save(metaTab);
						List<Field> fieldList= new ArrayList<Field>();
						List<Relationship>	relationshipList = new ArrayList<Relationship>();
						List<EnumField> enumFieldList = new ArrayList<EnumField>();
						
						List<Field> tabFieldList = new ArrayList<Field>();
						List<EnumField> tabEnumFieldList= new ArrayList<EnumField>();
						List<Relationship> tabRelationshipList = new ArrayList<Relationship>();

						for (it.polimi.utils.Field field: reflectionManager.getFieldByTabName(tabName))
						{
							if (reflectionManager.isKnownClass(field.getFieldClass()))
							{
								Field metaField= new Field();
								metaField.setName(field.getName());
								fieldRepository.save(metaField);
								FieldType fieldType=null;
								if (field.getFieldClass()==Integer.class) 
									fieldType=FieldType.INTEGER;
								if (field.getFieldClass()==String.class) 
									fieldType=FieldType.STRING;
								if (field.getFieldClass()==Date.class || field.getFieldClass()==java.sql.Date.class)
									fieldType=FieldType.DATE;
								if (field.getFieldClass()==Double.class) 
									fieldType=FieldType.DOUBLE;
								if (field.getFieldClass()==BigDecimal.class || field.getFieldClass()==Long.class) 
									fieldType=FieldType.LONG;
								if (field.getFieldClass()==Boolean.class)
									fieldType=FieldType.BOOLEAN;
								if (field.getFieldClass()==Time.class)
									fieldType=FieldType.TIME;
								metaField.setFieldType(fieldType);
								metaField.setEntity(entity);
								List<it.polimi.model.field.Annotation> annotationList = new ArrayList<it.polimi.model.field.Annotation>();
								convertAnnotation(field, metaField, annotationList);
								fieldRepository.save(metaField);
								fieldList.add(metaField);
								tabFieldList.add(metaField);
								continue;
							} // fine is known class
							if(field.getIsEnum())
							{
								EnumField enumField = new EnumField();
								enumField.setName(field.getName());
								enumField.setEntity(entity);
								enumFieldRepository.save(enumField);
								List<it.polimi.model.field.Annotation> annotationList = new ArrayList<it.polimi.model.field.Annotation>();
								convertAnnotation(field, enumField, annotationList);
								List<String> enumValueList = field.getEnumValuesList();
								List<EnumValue> metaEnumValueList = new ArrayList<EnumValue>();
								for (int i=0; i<enumValueList.size(); i++)
								{
									EnumValue metaEnumValue= new EnumValue();
									metaEnumValue.setName(enumValueList.get(i));
									metaEnumValue.setEnumField(enumField);
									metaEnumValue.setValue(i);
									enumValueRepository.save(metaEnumValue);
									metaEnumValueList.add(metaEnumValue);
								}
								enumField.setEnumValueList(metaEnumValueList);
								enumFieldRepository.save(enumField);
								enumFieldList.add(enumField);
								tabEnumFieldList.add(enumField);
								continue;
							}
							{ //relationship
								Relationship relationship = new Relationship();
								relationship.setEntity(entity);
								relationship.setName(reflectionManager.parseName(field.getName()));
								relationshipRepository.save(relationship);
								List<it.polimi.model.field.Annotation> annotationList = new ArrayList<it.polimi.model.field.Annotation>();
								convertAnnotation(field, relationship, annotationList);
								RelationshipType relationshipType = null;
								if (ReflectionManager.hasOneToOne(field))
								{
									relationshipType=RelationshipType.ONE_TO_ONE;
								}
								if (ReflectionManager.hasOneToMany(field))
								{
									relationshipType=RelationshipType.ONE_TO_MANY;
								}
								if (ReflectionManager.hasManyToOne(field))
								{
									relationshipType=RelationshipType.MANY_TO_ONE;
								}
								if (ReflectionManager.hasManyToMany(field))
								{
									relationshipType=RelationshipType.MANY_TO_MANY;
								}
								if (ReflectionManager.hasBackManyToMany(field))
								{
									relationshipType=RelationshipType.MANY_TO_MANY_BACK;
								}
								relationship.setEntityTarget(entityMap.get(reflectionManager.parseName(field.getCompositeClass().fullName())));
								
								relationship.setRelationshipType(relationshipType);
								relationshipRepository.save(relationship);
								
								relationshipList.add(relationship);
								tabRelationshipList.add(relationship);
								continue;
							}
							
						}
						metaTab.setFieldList(tabFieldList);
						metaTab.setEnumFieldList(tabEnumFieldList);
						metaTab.setRelationshipList(tabRelationshipList);
						tabRepository.save(metaTab);
						entity.setFieldList(fieldList);
						entity.setRelationshipList(relationshipList);
						entity.setEnumFieldList(enumFieldList);
						tabList.add(metaTab);
					} // fine ciclo entity
					entity.setTabList(tabList);
					entityRepository.save(entity);
					entityList.add(entity);
				}
				
				entityGroup.setEntityList(entityList);
				entityGroup.setProject(project);
				entityGroupRepository.save(entityGroup);
		}
	}
	
	private void convertAnnotation(it.polimi.utils.Field field,EntityAttribute metaEntityAttribute, List<it.polimi.model.field.Annotation> annotationList)
	{
		Annotation[] annotationArray = field.getAnnotationList();
		
		
		for (int i=0; i<annotationArray.length; i++)
		{
			it.polimi.model.field.Annotation metaAnnotation = new it.polimi.model.field.Annotation();
			AnnotationType annotationType= null;
			if (annotationArray[i].annotationType()==Id.class)
			{
				annotationType=AnnotationType.PRIMARY_KEY;
			}
			if (annotationArray[i].annotationType()==Between.class)
			{
				annotationType=AnnotationType.BETWEEN_FILTER;
			}
			if (annotationArray[i].annotationType()==DescriptionField.class)
			{
				annotationType=AnnotationType.DESCRIPTION_FIELD;
			}
			if (annotationArray[i].annotationType()==ExcelExport.class)
			{
				annotationType=AnnotationType.EXCEL_EXPORT;
			}
			if (annotationArray[i].annotationType()==Filter.class)
			{
				annotationType=AnnotationType.FILTER_FIELD;
			}
			if (annotationArray[i].annotationType()==IgnoreSearch.class)
			{
				annotationType=AnnotationType.IGNORE_SEARCH;
			}
			if (annotationArray[i].annotationType()==IgnoreTableList.class)
			{
				annotationType=AnnotationType.IGNORE_TABLE_LIST;
			}
			if (annotationArray[i].annotationType()==IgnoreUpdate.class)
			{
				annotationType=AnnotationType.IGNORE_UPDATE;
			}
			if (annotationArray[i].annotationType()==Size.class)
			{
				annotationType=AnnotationType.SIZE;
				List<AnnotationAttribute> annotationAttributeList = new ArrayList<AnnotationAttribute>();
				for (Method method : annotationArray[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("min") || method.getName().equals("max"))
					{
						Object value= null;
						try {
							value=  method.invoke(annotationArray[i], (Object[])null);
							AnnotationAttribute annotationAttribute = new AnnotationAttribute();
							annotationAttribute.setProperty(method.getName());
							annotationAttribute.setValue(value.toString());
							annotationAttribute.setAnnotation(metaAnnotation);
							annotationAttributeRepository.save(annotationAttribute);
							annotationAttributeList.add(annotationAttribute);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				metaAnnotation.setAnnotationAttributeList(annotationAttributeList);
			}
			if (annotationArray[i].annotationType()==NotNull.class)
			{
				annotationType=AnnotationType.NOT_NULL;
			}
			if (annotationArray[i].annotationType()==NotBlank.class)
			{
				annotationType=AnnotationType.NOT_BLANK;
			}
			
			if (annotationType!=null)
			{
				metaAnnotation.setAnnotationType(annotationType);
				if (metaEntityAttribute.isField())
					metaAnnotation.setField(metaEntityAttribute.asField());
				if (metaEntityAttribute.isEnumField())
					metaAnnotation.setEnumField(metaEntityAttribute.asEnumField());
				if (metaEntityAttribute.isRelationship())
					metaAnnotation.setRelationship(metaEntityAttribute.asRelationship());
				//annotationRepository.save(metaAnnotation);
				annotationRepository.save(metaAnnotation);
				annotationList.add(metaAnnotation);
			}
		}
		if (metaEntityAttribute.isField())
			metaEntityAttribute.asField().setAnnotationList(annotationList);
		if (metaEntityAttribute.isEnumField())
			metaEntityAttribute.asEnumField().setAnnotationList(annotationList);
		if (metaEntityAttribute.isRelationship())
			metaEntityAttribute.asRelationship().setAnnotationList(annotationList);

	}

}
